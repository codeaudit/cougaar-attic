/* -*- JAVA -*- */

// ****** Code generated by the QuO codegenerator 3.0.9 ******
// QuO and the QuO codegenerator have been developed by 
// BBN Technologies.

// Information about QuO is available at:
// http://www.dist-systems.bbn.com/tech/QuO



package org.cougaar.lib.quo ;

abstract public class LoggingQosketSkel
         implements org.cougaar.lib.quo.LoggingQosket
{
  // Sysconds from contract arglist

  // Callbacks from contract arglist

  // Syscondseqs from contract arglist

  // Callbacks from contract arglist

  // Contract
  protected com.bbn.quo.rmi.Contract quo_Logging;

  // Qosket members

  // Subclass Responsibility to override this function 
  public void initSysconds  (com.bbn.quo.rmi.QuoKernel kernel)
  throws java.rmi.RemoteException 
  {
  }

  // Subclass Responsibility to override this function 
  public void initCallbacks ()
  { 
  } 

  public com.bbn.quo.rmi.Contract initContract(String name, 
                                               com.bbn.quo.rmi.QuoKernel kernel)
  throws java.rmi.RemoteException 
  {
    com.bbn.quo.rmi.Contract result = null;
    if (name.equals ("Logging")) {
      com.bbn.quo.rmi.SysCond[] sysconds =  new com.bbn.quo.rmi.SysCond[0]; 

      com.bbn.quo.rmi.Callback[] callbacks =  new com.bbn.quo.rmi.Callback[0]; 

      quo_Logging = kernel.bindContract("Logging", "org::cougaar::lib::quo::Logging", sysconds, callbacks ); 
    result = quo_Logging;
    } else {
    } // else 
    return result;
  } // initContract

  public com.bbn.quo.rmi.Contract initContract(String name, 
                                               String interfaceName,
                                               com.bbn.quo.rmi.QuoKernel kernel)
  throws java.rmi.RemoteException 
  {
    com.bbn.quo.rmi.SysCond[] sysconds =  new com.bbn.quo.rmi.SysCond[0]; 

    com.bbn.quo.rmi.Callback[] callbacks =  new com.bbn.quo.rmi.Callback[0]; 

    return kernel.bindContract(name, interfaceName, sysconds, callbacks ); 
  } // initContract

} // class

