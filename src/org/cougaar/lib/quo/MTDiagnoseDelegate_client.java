/* -*- JAVA -*- */

// ****** Code generated by the QuO codegenerator 3.0.9 ******
// QuO and the QuO codegenerator have been developed by 
// BBN Technologies.

// Information about QuO is available at:
// http://www.dist-systems.bbn.com/tech/QuO


package org.cougaar.lib.quo ;

import java.rmi.*;
import com.bbn.quo.rmi.*;
import com.bbn.quo.*;

public class MTDiagnoseDelegate_client implements org.cougaar.core.mts.MT {

  //These are instance variables declared in ASL.
  protected org.cougaar.lib.quo.DiagnoseDelegateQosket qk;
  protected org.cougaar.core.mts.MT remote;

  void set_qk(org.cougaar.lib.quo.DiagnoseDelegateQosket arg1){
    qk = arg1;
  }
  void set_remote(org.cougaar.core.mts.MT arg1){
    remote = arg1;
  }
  void set_remoteObj(org.cougaar.core.mts.MT arg1){
    remote = arg1;
  }
  org.cougaar.core.mts.MT  get_remoteObj(){
    return(remote);
  }
  protected com.bbn.quo.rmi.Contract quo_Diagnose;
  public void set_contract_Diagnose(com.bbn.quo.rmi.Contract arg1){
    quo_Diagnose = arg1;
  }
  public com.bbn.quo.rmi.Contract get_contract_Diagnose() {
    return quo_Diagnose;
  }

  void set_qosket(org.cougaar.lib.quo.DiagnoseDelegateQosket qs){
    qk = qs;
  }
  final static int DIAGNOSE__NORMAL = 0;

  public void rerouteMessage(org.cougaar.core.mts.AttributedMessage m) throws RemoteException {
    // Default declarations and setup of quo introduced variables local to method:
    int[] quo_curRegs_Diagnose = null;
    com.bbn.quo.corba.Association [] sig;
    java.lang.String methodID;
    int length;
    com.bbn.quo.instr.corba.Trace_rec rec;
    com.bbn.quo.corba.SignalEvent quo_sig1;
    com.bbn.quo.corba.SignalEvent quo_sig2;

    // Adaptive code for before methodentry
    methodID = "rerouteMessage";
    rec = qk.createTraceRec(methodID);
    // Adaptive code for after methodentry
    length = 0;
    // Adaptive code for before premethodcontracteval
    qk.clientRequestIn1(rec, length);
    sig = qk.createSignalAssociations(rec);
    quo_sig1 = 	new com.bbn.quo.corba.SignalEvent("premethod", sig);
    try { 
quo_curRegs_Diagnose = quo_Diagnose.signalAndEvalAndGetCurrentRegion(quo_sig1);
     } catch (java.rmi.RemoteException pre_meth_ceval_ex) {} 
      // Adaptive code for after premethodcontracteval
      qk.clientRequestOut1(rec, length);
      // Adaptive code for inplaceof methodcall
      qk.runDiagnostic(m, remote);
      // Adaptive code for after methodcall
      length = 0;
      // Adaptive code for before postmethodcontracteval
      qk.clientReplyIn1(rec, length);
      sig = qk.createSignalAssociations(rec);
      // Adaptive code for before postmethodcontracteval
      quo_sig2 = 	new com.bbn.quo.corba.SignalEvent("postmethod", sig);
      try { 
quo_curRegs_Diagnose = quo_Diagnose.signalAndEvalAndGetCurrentRegion(quo_sig2);
             } catch( java.rmi.RemoteException post_meth_ceval_ex) {} 
        // Adaptive code for before methodreturn
        qk.clientReplyOut1(rec, length);
        qk.registerCall(rec);
        return;
      }
      public org.cougaar.core.mts.MessageAddress getMessageAddress() throws RemoteException {
        // Default declarations and setup of quo introduced variables local to method:
        int[] quo_curRegs_Diagnose = null;
        com.bbn.quo.corba.Association[] sig =
            new com.bbn.quo.corba.Association[0];
        org.cougaar.core.mts.MessageAddress quo_retval;
        com.bbn.quo.corba.SignalEvent quo_sig1;
        com.bbn.quo.corba.SignalEvent quo_sig2;

        quo_retval = remote.getMessageAddress();
        return(quo_retval);
      }
};

