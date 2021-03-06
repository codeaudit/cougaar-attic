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

package org.cougaar.lib.contract.lang.parser;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.apache.xerces.parsers.DOMParser;
import org.cougaar.lib.contract.lang.ParseException;
import org.cougaar.lib.contract.lang.TreeVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * An XML styled <code>Op</code> parser which can be used to control 
 * a <code>TreeVisitor</code>.
 * <p>
 * <pre>
 * For example,<code>
 *    &lt;and&gt;
 *      &lt;or&gt;
 *        &lt;isNull/&gt;
 *        &lt;true/&gt;
 *      &lt;/or&gt;
 *      &lt;false&gt;
 *    &lt;/and&gt;</code>
 * becomes essentially<code>
 *    (and (or (isNull (true))) (false))</code></pre>
 * <p>
 * @see ParenParser for "semi-Lisp" implementation
 */
public class XMLParser {

  private XMLParser() { }

  public static void parse(
      TreeVisitor visitor, Object o) throws ParseException {
    // convert to DOM Element
    Element elem;
    if (o instanceof Element) {
      elem = (Element)o;
    } else {
      if (o instanceof String) {
        o = new StringReader((String)o);
      }
      if (o instanceof Reader) {
        try {
          DOMParser parser = new DOMParser();
          parser.parse(new InputSource((Reader)o));
          Document document = parser.getDocument();
          elem = document.getDocumentElement();
        } catch (Exception e) {
          throw new ParseException(
            "Unable to tokenize XML \"Reader\":\n"+
            e);
        }
      } else if (o instanceof InputStream) {
        try {
          DOMParser parser = new DOMParser();
          parser.parse(new InputSource((InputStream)o));
          Document document = parser.getDocument();
          elem = document.getDocumentElement();
        } catch (Exception e) {
          throw new ParseException(
            "Unable to tokenize XML \"InputStream\":\n"+
            e);
        }
      } else {
        throw new ParseException(
          "Unable to convert "+
          ((o != null) ? o.getClass().getName() : "null"));
      }
      if (elem == null) {
        throw new ParseException(
          "Unable to parse XML!  Element is null.");
      }
    }

    visitor.initialize();

    // parse the DOM
    parseElem(visitor, elem);

    visitor.visitEndOfTree();
  }

  /**
   * recursive!
   */
  protected static void parseElem(TreeVisitor visitor, Element elem) {
    String nodeName = elem.getNodeName();
    if (nodeName.equals("const")) {
      // special introduction of constant
      parseConstElem(visitor, elem);
      return;
    }
    visitor.visitWord(nodeName);
    // add child nodes
    NodeList nlist = elem.getChildNodes();
    int nlength = nlist.getLength();
    Node subNode = null;
    for (int i = 0; ; i++) {
      if (i >= nlength) {
        if (i == 1) {
          // leaf
          String leafVal = subNode.getNodeValue();
          if (leafVal != null) {
            leafVal = leafVal.trim();
            if (leafVal.length() > 0) {
              // single string argument, e.g. "<equals>foo</equals>",
              // becomes "<equals><const value=\"foo\"/></equals>"
              visitor.visitConstant(leafVal);
            }
          }
        }
        break;
      }
      subNode = nlist.item(i);
      if (subNode.getNodeType() == Node.ELEMENT_NODE) {
        // recurse!
        parseElem(visitor, (Element)subNode);
      }
    }
    // add tail paren
    visitor.visitEnd();
  }

  /**
   * Special introduction of constant -- expecting single value.
   */
  protected static void parseConstElem(TreeVisitor visitor, Element elem) {
    // find optional attributes "type" and "value"
    String constType = null;
    String constVal = null;
    NamedNodeMap attribs = elem.getAttributes();
    if (attribs != null) {
      int nAttribs = attribs.getLength();
      for (int i = 0; i < nAttribs; i++) {
        Node aNode = attribs.item(i);
        String aName = aNode.getNodeName();
        // examine attribute name
        switch (aName.length()) {
          case 1:
            {
              char ch = aName.charAt(0);
              if (ch == 't') {
                // found type
                constType = aNode.getNodeValue();
              } else if (ch == 'v') {
                // found value
                constVal = aNode.getNodeValue();
              }
              break;
            }
          case 4:
            if (aName.equalsIgnoreCase("type")) {
              // found type
              constType = aNode.getNodeValue();
            }
            break;
          case 5:
            if (aName.equalsIgnoreCase("value")) {
              // found value
              constVal = aNode.getNodeValue();
            }
            break;
          case 0: /* fall-through */
          case 2: /* fall-through */
          case 3: /* fall-through */
          default:
            break;
        }
      }
    }
    // find the constant value
    if (constVal == null) {
      NodeList nlist = elem.getChildNodes();
      if (nlist.getLength() == 1) {
        Node subNode = nlist.item(0);
        if (subNode.getNodeType() != Node.ELEMENT_NODE) {
          String nval = subNode.getNodeValue();
          if (nval != null) {
            nval = nval.trim();
            if (nval.length() > 0) {
              // found the value
              constVal = nval;
            }
          }
        }
      }
      // check if the constant value was set
      if (constVal == null) {
        throw new IllegalArgumentException(
          "Invalid \"const\" value!  XML Element: "+elem);
      }
    }
    // append the constant
    if (constType == null) {
      // string
      visitor.visitConstant(constVal);
    } else {
      // use given type
      visitor.visitConstant(constType, constVal);
    }
  }

  public static StringVisitor getStringVisitor() {
    return new XMLStringVisitor();
  }

  public static XMLBuilderVisitor getXMLVisitor(Document doc) {
    return new XMLBuilderVisitor(doc);
  }

  public static String toString(VisitTokenizer visTokenizer) {
    TreeVisitor visitor = getStringVisitor();
    VisitReplayer.replay(visitor, visTokenizer);
    return visitor.toString();
  }

  public static void main(String[] args) {
    String input = 
      "<and><a/><const value=\"x\"/> "+
      "<c> <const type=\"t\" value=\"v\"/> <e/></c></and>";
    System.out.print("Given: "+input+"\nParsed: ");
    try {
      TreeVisitor strVis = XMLParser.getStringVisitor();
      XMLParser.parse(strVis, input);
      System.out.println(strVis.toString());
    } catch (Exception e) {
      System.out.println("\n######\n"+e);
      e.printStackTrace();
    }
  }
}
