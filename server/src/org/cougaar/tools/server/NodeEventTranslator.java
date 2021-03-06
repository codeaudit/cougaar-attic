/*
 * <copyright>
 *  
 *  Copyright 1997-2004 BBNT Solutions, LLC
 *  under sponsorship of the Defense Advanced Research Projects
 *  Agency (DARPA).
 * 
 *  You can redistribute this software and/or modify it under the
 *  terms of the Cougaar Open Source License as published on the
 *  Cougaar Open Source Website (www.cougaar.org).
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  
 * </copyright>
 */
 
package org.cougaar.tools.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a <i>temporary</i> translator class for 
 * OutputBundles to Lists of NodeEvents.
 * <p>
 * See the server example "GuiConsole" for an example
 * use of ObjectBundles.
 *
 * @deprecated use OutputBundles directly, which is more 
 *    efficient. <b>Note</b> that NodeEvent will also be 
 *    deprecated soon.
 */
public final class NodeEventTranslator {

  private NodeEventTranslator() {
    // just utility methods!
  }

  /**
   * Translate a single NodeEvent into an OutputBundle.
   */
  public static OutputBundle fromNodeEvents(
      NodeEvent ne) {
    List l = new ArrayList(1);
    l.add(ne);
    return fromNodeEvents(l);
  }

  /**
   * Translate a List of NodeEvents ("l") into an OutputBundle.
   */
  public static OutputBundle fromNodeEvents(
      List l) {
    OutputBundle ob = new OutputBundle();
    fromNodeEvents(ob, l);
    return ob;
  }

  /**
   * Translate an OutputBundle ("ob") into a List of NodeEvents.
   */
  public static List toNodeEvents(OutputBundle ob) {
    List l = new ArrayList();
    toNodeEvents(l, ob);
    return l;
  }


  /**
   * Translate a List of NodeEvents ("l") into an OutputBundle "ob".
   * <p>
   * Assumes that no other thread(s) are using using either
   * "ob" and/or "l" (thread-safety).
   */
  public static void fromNodeEvents(
      OutputBundle ob, // to ob
      List l  // from l
      ) {
    List idleUpdates = ob.getIdleUpdates();
    DualStreamBuffer dsb = ob.getDualStreamBuffer();
    OutputStream out = dsb.getOutputStream(true);
    OutputStream err = dsb.getOutputStream(false);
    int n = l.size();
    for (int i = 0; i < n; i++) {
      NodeEvent ni = (NodeEvent) l.get(i);
      switch (ni.getType()) {
        case NodeEvent.STANDARD_OUT: 
          {
            String msg = ni.getMessage();
            byte[] b = msg.getBytes();
            try {
              out.write(b);
            } catch (IOException ioe) {
              throw new InternalError("never?");
            }
          }
          break;
        case NodeEvent.STANDARD_ERR:
          {
            String msg = ni.getMessage();
            byte[] b = msg.getBytes();
            try {
              err.write(b);
            } catch (IOException ioe) {
              throw new InternalError("never?");
            }
          }
          break;
        case NodeEvent.HEARTBEAT:
          // ignore
          break;
        case NodeEvent.PROCESS_CREATED:
          ob.setCreated(true);
          break;
        case NodeEvent.IDLE_UPDATE:
          {
            String msg = ni.getMessage();
            idleUpdates.add(msg);
          }
          break;
        case NodeEvent.PROCESS_DESTROYED:
          ob.setDestroyed(true);
          break;
        default:
        break;
      }
    }
  }

  /**
   * Translate an OutputBundle ("ob") into a List of NodeEvents ("l").
   * <p>
   * Assumes that no other thread(s) are using using either
   * "ob" and/or "l" (thread-safety).
   */
  public static void toNodeEvents(
      final List l, // to l
      OutputBundle ob // to ob
      ) {
    if (ob.getCreated()) {
      l.add(new NodeEvent(NodeEvent.PROCESS_CREATED));
    }
    List idleUpdates = ob.getIdleUpdates();
    if (idleUpdates != null) {
      int n = idleUpdates.size();
      for (int i = 0; i < n; i++) {
        String si = (String) idleUpdates.get(i);
        l.add(new NodeEvent(NodeEvent.IDLE_UPDATE, si));
      }
    }
    OutputStream out = 
      new OutputStream() {
        @Override
      public void write(int b) {
          String msg = Byte.toString((byte) b);
          l.add(new NodeEvent(NodeEvent.STANDARD_OUT, msg));
        }
        @Override
      public void write(byte[] b, int off, int len) {
          String msg = new String(b, off, len);
          l.add(new NodeEvent(NodeEvent.STANDARD_OUT, msg));
        }
      };
    OutputStream err = 
      new OutputStream() {
        @Override
      public void write(int b) {
          String msg = Byte.toString((byte) b);
          l.add(new NodeEvent(NodeEvent.STANDARD_ERR, msg));
        }
        @Override
      public void write(byte[] b, int off, int len) {
          String msg = new String(b, off, len);
          l.add(new NodeEvent(NodeEvent.STANDARD_ERR, msg));
        }
      };
    DualStreamBuffer dsb = ob.getDualStreamBuffer();
    try {
      dsb.writeTo(out, err);
    } catch (IOException ioe) {
      throw new InternalError("never?");
    }
    if (ob.getDestroyed()) {
      l.add(new NodeEvent(NodeEvent.PROCESS_DESTROYED));
    }
  }

}
