/*
 * <copyright>
 *  Copyright 1997-2003 BBNT Solutions, LLC
 *  under sponsorship of the Defense Advanced Research Projects Agency (DARPA).
 * 
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the Cougaar Open Source License as published by
 *  DARPA on the Cougaar Open Source Website (www.cougaar.org).
 * 
 *  THE COUGAAR SOFTWARE AND ANY DERIVATIVE SUPPLIED BY LICENSOR IS
 *  PROVIDED 'AS IS' WITHOUT WARRANTIES OF ANY KIND, WHETHER EXPRESS OR
 *  IMPLIED, INCLUDING (BUT NOT LIMITED TO) ALL IMPLIED WARRANTIES OF
 *  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND WITHOUT
 *  ANY WARRANTIES AS TO NON-INFRINGEMENT.  IN NO EVENT SHALL COPYRIGHT
 *  HOLDER BE LIABLE FOR ANY DIRECT, SPECIAL, INDIRECT OR CONSEQUENTIAL
 *  DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE OF DATA OR PROFITS,
 *  TORTIOUS CONDUCT, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 *  PERFORMANCE OF THE COUGAAR SOFTWARE.
 * </copyright>
 */
 
package org.cougaar.tools.server;

import org.cougaar.tools.server.rmi.RMIRegistry;

/**
 * Registry for app-servers.
 * <p>
 * Currently only the RMI-based registry is supported.
 */
public abstract class RemoteHostRegistry {

  /**
   * Get an instance of the registry.
   */
  public static RemoteHostRegistry getInstance() {
    // could cache:
    return new RMIRegistry();
  }

  /**
   * Client method for contacting a remote host.
   */
  public abstract RemoteHost lookupRemoteHost(
      String hostName, 
      int hostPort,
      boolean verbose) throws Exception;

  /**
   * Server method for registering itself.
   */
  public abstract void bindRemoteHost(
      RemoteHost rh,
      int port,
      boolean verbose) throws Exception;

}
