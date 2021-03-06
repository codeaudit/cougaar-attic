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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Immutable description for a remote process.
 */
public final class ProcessDescription 
implements java.io.Serializable
{

  // Make the props and args mutable.  This simplifies 
  // some client code, but is generally a bad idea.
  private static final boolean MAKE_IMMUTABLE = Boolean.getBoolean("org.cougaar.tools.server.immutable");

  private final String name;
  private final String group;
  private final Map javaProps;
  private final List commandLineArgs;

  public ProcessDescription(
      String name,
      String group,
      Map javaProps,
      List commandLineArgs) {
    this.name = prepareName(name);
    this.group = prepareGroup(group);
    this.javaProps = prepareProps(javaProps);
    this.commandLineArgs = prepareArgs(commandLineArgs);
  }

  /**
   * Get the "name" identifier for this process.
   * <p>
   * All processes must have globally-unique names.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the optional "group" identifier for this process.
   * <p>
   * This can be used to tag multiple processes into logical
   * groups.
   */
  public String getGroup() {
    return group;
  }

  /**
   * Get the java properties, which is an unmodifiable map
   * of (String, String) pairs.
   * <p>
   * All properties fall into three groups: <ul>
   *   <li>"<b>env.*</b>" process environment properties 
   *       (e.g. "env.DISPLAY=..")</li>
   *   <li>"<b>java.*</b>" java options 
   *       (e.g. "java.class.path=..")</li>
   *   <li>"<b>*</b>" for all other "-D" system properties 
   *       (e.g. "foo=bar")</li>
   * </ul><br>
   * If the value is <tt>null</tt> then this property is
   * to be <i>removed</i> from the default list of properties
   * (if one is specified on the server).
   * <p>
   * Double-prefixes ("env.env.*" and "java.java.*") are 
   * stripped and converted to "-D" properties.  For example,
   * "java.java.duck=quack" is converted to "-Djava.duck=quack".
   * <p>
   * Some properties of note:<ul>
   *   <li>"java.jar=" specify an java executable jar, this
   *       property defaults to empty (no executable jar)</li>
   *   <li>"java.class.name=" to set the classname, which
   *       is required if the "java.jar=.." is not specified.
   *       If both the classname and jar are specified then
   *       the classname is ignored.</li>
   *   <li>"java.class.path=" specify the java classpath -- note 
   *       that this *doesn't* adopt the server's classpath!  
   *       If the "java.jar=.." is specified then this property
   *       is ignored.  If both this property and "java.jar=.."
   *       are not specified then this property defaults to the 
   *       system-default classpath</li>
   *   <li>"java.jvm.program=" to set the java executable
   *       (defaults to "${java.home}/bin/java")</li>
   *   <li>"java.jvm.mode=" to set the JVM mode 
   *       ("classic", "hotspot", "client", or "server");
   *       defaults to "hotspot"</li>
   *   <li>"java.jvm.green=" to add "-green" for green threads,
   *       defaults to false.
   *   <li>"java.Xbootclasspath[/p|/a]=" to set the bootclasspath.
   *       "java.Xbootclasspath/p=" is used to prefix the default
   *       bootclasspath, or "java.Xbootclasspath/a=" to append</li>
   *   <li>"java.heap.min=" to specify the minimum heap size,
   *       such as "100m" for 100 megabytes; defaults to a
   *       system-specify value</li>
   *   <li>"java.heap.max=" to specify the maximum heap size,
   *       such as "300m" for 300 megabytes; defaults to a
   *       system-specify value</li>
   *   <li>"java.stack.size=" to specify the Java thread stack size,
   *       such as "10m" for 10 megabytes; defaults to a 
   *       system-specify value</li>
   *   <li>"java.enable.assertions[=(|true|false|package|classname)]
   *       to turn on JDK 1.4 assertion checking (-ea)</li>
   *   <li>"java.disable.assertions[=(|true|false|package|classname)]
   *       to turn off JDK 1.4 assertion checking (-da)</li>
   *   <li>"java.enable.system.assertions[=(|true|false])]
   *       to turn on JDK 1.4 system assertion checking (-esa)</li>
   *   <li>"java.disable.system.assertions[=(|true|false])]
   *       to turn off JDK 1.4 system assertion checking (-dsa)</li>
   *   <li>"java.*" for all other "-*" properties, such as 
   *       "java.Xnoclassgc=" for "-Xnoclassgc"</li>
   * </ul>
   */
  public Map getJavaProperties() {
    return javaProps;
  }

  /**
   * Get the command-line arguments, which is an unmodifiable
   * list of non-null Strings.
   * <p>
   * These arguments are in addition to the optional 
   * java-properties.
   */
  public List getCommandLineArguments() {
    return commandLineArgs;
  }

  @Override
public String toString() { 
    return 
      "Process \""+name+
      "\" of group \""+group+"\"";
  }

  @Override
public int hashCode() {
    // could cache
    return name.hashCode();
  }

  @Override
public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ProcessDescription)) {
      return false;
    }
    ProcessDescription pd = (ProcessDescription) o;
    if (!(name.equals(pd.name))) {
      return false;
    }
    if (!(group.equals(pd.group))) {
      return false;
    }
    if (!(javaProps.equals(pd.javaProps))) {
      return false;
    }
    if (!(commandLineArgs.equals(pd.commandLineArgs))) {
      return false;
    }
    return true;
  }

  private static String prepareName(String s) {
    if (s == null) {
      throw new IllegalArgumentException(
          "Name must be non-null");
    }
    return s;
  }

  private static String prepareGroup(String s) {
    if (s == null) {
      return "<none>";
    }
    return s;
  }

  /**
   * Does three things:
   * <ol>
   *   <li>Use a zero-size map if passed null</li>
   *   <li>Flatten "java.util.Properies" to a "java.util.HashMap",
   *       since Properties has bad "get(..)" and "size()" 
   *       implementations</li>
   *   <li>Wrap the map into an unmodifiable map</li>
   * </ol>.
   */
  private static Map prepareProps(Map m) {
    if (!MAKE_IMMUTABLE) {
      // FIXME check for Properties with non-null defaults,
      // since sometimes "getProperty(..)" != "get(..)"
      return m;
    }
    if (m == null) {
      return Collections.EMPTY_MAP;
    }
    // flatten properties
    Map nm = m;
    if (m instanceof Properties) {
      Properties props = (Properties) m;
      nm = new HashMap();
      for (Enumeration en = props.propertyNames();
          en.hasMoreElements();
          ) {
        String name = (String) en.nextElement();
        String value = props.getProperty(name);
        nm.put(name, value);
      }
    } else {
      // verify (String, String) pairs
    }
    // force immutable
    Map ret = Collections.unmodifiableMap(nm);
    return ret;
  }

  /**
   * Does three things:
   * <ol>
   *   <li>Use a zero-size list if passed null</li>
   *   <li>Make sure the list only contains non-null Strings</li>
   *   <li>Wrap the list into an unmodifiable list</li>
   * </ol>.
   */
  private static List prepareArgs(List l) {
    if (!MAKE_IMMUTABLE) {
      return l;
    }
    if (l == null) {
      return Collections.EMPTY_LIST;
    }
    // check for strings
    int n = l.size();
    for (int i = 0; i < n; i++) {
      Object oi = l.get(i);
      if (!(oi instanceof String)) {
        throw new IllegalArgumentException(
            "Command line argument ["+i+
            "] is not a String: "+oi);
      }
    }
    // force immutable
    List ret = Collections.unmodifiableList(l);
    return ret;
  }

  private static final long serialVersionUID = 2938472839235681283L;
}
