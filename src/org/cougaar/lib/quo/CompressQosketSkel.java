/* -*- JAVA -*- */

// ****** Code generated by the QuO codegenerator 3.0.9 ******
// QuO and the QuO codegenerator have been developed by 
// BBN Technologies.

// Information about QuO is available at:
// http://www.dist-systems.bbn.com/tech/QuO



package org.cougaar.lib.quo ;

abstract public class CompressQosketSkel
         implements org.cougaar.lib.quo.CompressQosket, 
         org.cougaar.lib.quo.CompressDelegateQosket
{
  // Sysconds from contract arglist
  protected org.cougaar.core.qos.rss.MetricSC expectedServerEffectiveMJips;
  protected org.cougaar.core.qos.rss.MetricSC expectedClientEffectiveMJips;
  protected org.cougaar.core.qos.rss.MetricSC expectedNetworkCapacity;
  protected com.bbn.quo.rmi.ValueSC UseCompression;

  // Callbacks from contract arglist

  // Syscondseqs from contract arglist

  // Callbacks from contract arglist

  // Contract
  protected com.bbn.quo.rmi.Contract quo_Compress;

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
    if (name.equals ("Compress")) {
      com.bbn.quo.rmi.SysCond[] sysconds =  new com.bbn.quo.rmi.SysCond[4]; 
      sysconds[0] = expectedServerEffectiveMJips;
      sysconds[1] = expectedClientEffectiveMJips;
      sysconds[2] = expectedNetworkCapacity;
      sysconds[3] = UseCompression;

      com.bbn.quo.rmi.Callback[] callbacks =  new com.bbn.quo.rmi.Callback[0]; 

      quo_Compress = kernel.bindContract("Compress", "org::cougaar::lib::quo::Compress", sysconds, callbacks ); 
    result = quo_Compress;
    } else {
    } // else 
    return result;
  } // initContract

  public com.bbn.quo.rmi.Contract initContract(String name, 
                                               String interfaceName,
                                               com.bbn.quo.rmi.QuoKernel kernel)
  throws java.rmi.RemoteException 
  {
    com.bbn.quo.rmi.SysCond[] sysconds =  new com.bbn.quo.rmi.SysCond[4]; 
      sysconds[0] = expectedServerEffectiveMJips;
      sysconds[1] = expectedClientEffectiveMJips;
      sysconds[2] = expectedNetworkCapacity;
      sysconds[3] = UseCompression;

    com.bbn.quo.rmi.Callback[] callbacks =  new com.bbn.quo.rmi.Callback[0]; 

    return kernel.bindContract(name, interfaceName, sysconds, callbacks ); 
  } // initContract

} // class

