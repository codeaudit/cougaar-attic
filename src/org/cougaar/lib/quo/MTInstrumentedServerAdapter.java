/* -*- JAVA -*- */

// ****** Code generated by the QuO codegenerator 3.0.8 ******
// QuO and the QuO codegenerator have been developed by 
// BBN Technologies.

// Information about QuO is available at:
// http://www.dist-systems.bbn.com/tech/QuO



package org.cougaar.lib.quo ;

public class MTInstrumentedServerAdapter
       extends org.cougaar.lib.quo.MTInstrumentedInstrumentedServerDelegate_object
       implements com.bbn.quo.qosket.instrumentation.ServerInstrumentationQosket 
{
  private com.bbn.quo.qosket.instrumentation.rmi.ServerInstrumentationQosketImpl qosket;

  // Constructor for BQAdapter
  public MTInstrumentedServerAdapter() 
  throws java.rmi.RemoteException 
  {
     this.qosket  = new com.bbn.quo.qosket.instrumentation.rmi.ServerInstrumentationQosketImpl();
     set_qosket (qosket);
  }

  // BQAdapter constructor for RMI, Serverside to handle socket factory 
  public MTInstrumentedServerAdapter( 
     int port, 
     java.rmi.server.RMISocketFactory client_sockfac, 
     java.rmi.server.RMISocketFactory server_sockfac
   ) throws java.rmi.RemoteException 
  {
     super(port, client_sockfac,server_sockfac); 
     this.qosket  = new com.bbn.quo.qosket.instrumentation.rmi.ServerInstrumentationQosketImpl();
     set_qosket (qosket);
  }

  public void initSysconds (com.bbn.quo.rmi.QuoKernel kernel)
  throws java.rmi.RemoteException 
  {
     qosket.initSysconds(kernel); 
  }

  public void initCallbacks ()
  {
     qosket.initCallbacks(); 
  }

  public void linkContract (com.bbn.quo.rmi.QuoKernel kernel)
  throws java.rmi.RemoteException 
  {
     set_contract_InstrumentedServer( qosket.initContract ("InstrumentedServer", kernel) );
  }

  public com.bbn.quo.rmi.Contract initContract(String name,
                           String interfaceName,
                           com.bbn.quo.rmi.QuoKernel kernel)
  throws java.rmi.RemoteException 
  {
     return qosket.initContract(name, interfaceName, kernel);
  }

  public void linkRemoteObject( org.cougaar.lib.quo.MTInstrumented remote_obj)
  {
     set_remoteObj ( remote_obj );
  }

  // Qosket methods from IDL
  // inherited methods and attributes of com.bbn.quo.qosket.instrumentation.ServerInstrumentationQosket

  // local methods and attributes of com.bbn.quo.qosket.instrumentation.ServerInstrumentationQosket
  public void setServer(java.lang.Object server)
  {
     qosket.setServer ( server );
  }

}

