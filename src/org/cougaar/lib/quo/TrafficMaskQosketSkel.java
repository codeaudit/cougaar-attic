/* -*- JAVA -*- */

// ****** Code generated by the QuO codegenerator 3.0.9 ******
// QuO and the QuO codegenerator have been developed by 
// BBN Technologies.

// Information about QuO is available at:
// http://www.dist-systems.bbn.com/tech/QuO



package org.cougaar.lib.quo ;

abstract public class TrafficMaskQosketSkel
         implements org.cougaar.lib.quo.TrafficMaskQosket
{
  // Sysconds from contract arglist
  protected com.bbn.quo.rmi.ExpectedBandwidthSC Bandwidth;
  protected com.bbn.quo.rmi.ValueSC useMask;
  protected com.bbn.quo.rmi.ValueSC trust;

  // Callbacks from contract arglist
  protected org.cougaar.lib.mquo.TrafficMaskControl trafficControl;

  // Syscondseqs from contract arglist

  // Callbacks from contract arglist

  // Contract
  protected com.bbn.quo.rmi.Contract quo_TrafficMask;

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
    if (name.equals ("TrafficMask")) {
      com.bbn.quo.rmi.SysCond[] sysconds =  new com.bbn.quo.rmi.SysCond[3]; 
      sysconds[0] = Bandwidth;
      sysconds[1] = useMask;
      sysconds[2] = trust;

      com.bbn.quo.rmi.Callback[] callbacks =  new com.bbn.quo.rmi.Callback[1]; 
      callbacks[0] = trafficControl;

      quo_TrafficMask = kernel.bindContract("TrafficMask", "org::cougaar::lib::quo::TrafficMask", sysconds, callbacks ); 
    result = quo_TrafficMask;
    } else {
    } // else 
    return result;
  } // initContract

  public com.bbn.quo.rmi.Contract initContract(String name, 
                                               String interfaceName,
                                               com.bbn.quo.rmi.QuoKernel kernel)
  throws java.rmi.RemoteException 
  {
    com.bbn.quo.rmi.SysCond[] sysconds =  new com.bbn.quo.rmi.SysCond[3]; 
      sysconds[0] = Bandwidth;
      sysconds[1] = useMask;
      sysconds[2] = trust;

    com.bbn.quo.rmi.Callback[] callbacks =  new com.bbn.quo.rmi.Callback[1]; 
      callbacks[0] = trafficControl;

    return kernel.bindContract(name, interfaceName, sysconds, callbacks ); 
  } // initContract

} // class

