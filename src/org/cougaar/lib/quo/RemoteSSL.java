/* -*- JAVA -*- $Id: RemoteSSL.java,v 1.3 2001-11-02 20:39:26 psharma Exp $ */

// ****** Code generated by the QuO codegenerator version 3.0.7 ******
// QuO and the QuO codegenerator have been developed by 
// BBN Technologies.

// Information about QuO is available at:
// http://www.dist-systems.bbn.com/tech/QuO


package org.cougaar.lib.quo;

import com.bbn.quo.rmi.*;
import com.bbn.quo.*;

public class RemoteSSL extends com.bbn.quo.rmi.impl.ContractImpl
{
  static ConstantSF constInt10000000;
  static ConstantSF constBooleantrue;

  static {
    constInt10000000 = new ConstantSF(new Integer(10000000));
    constBooleantrue = new ConstantSF(new Boolean(true));
  }


  public void init(SysCond[] scParams, SysCond[][] scSeqParams, Callback[] cbParams, Callback[][] cbSeqParams, Kernel kernel)
  {
  int child_count = 0;

    if (scParams.length != 3 || scSeqParams.length != 0 || cbParams.length != 0 || cbSeqParams.length != 0) {
      throw new RuntimeException("QuO bug: constructor called improperly");
    }

    callback_objs = cbParams;
    callback_seqs = cbSeqParams;
    syscond_objs = new SysCondImpl[3];
    syscond_seqs = new SysCondImpl[0][];


    com.bbn.quo.rmi.impl.SysCond  servant0 = com.bbn.quo.rmi.impl.SysCond.lookup(scParams[0]);
    com.bbn.quo.rmi.impl.SysCond  servant1 = com.bbn.quo.rmi.impl.SysCond.lookup(scParams[1]);
    com.bbn.quo.rmi.impl.SysCond  servant2 = com.bbn.quo.rmi.impl.SysCond.lookup(scParams[2]);

    com.bbn.quo.rmi.ExpectedCapacitySCTie tsc0 = (com.bbn.quo.rmi.ExpectedCapacitySCTie) servant0;
    com.bbn.quo.data.ExpectedCapacitySCImpl BandwidthSC = (com.bbn.quo.data.ExpectedCapacitySCImpl) tsc0._delegate();
    syscond_objs[0] = BandwidthSC;
    PrimitiveSF BandwidthSF =
      new PrimitiveSF(BandwidthSC);

    com.bbn.quo.rmi.ValueSCTie tsc1 = (com.bbn.quo.rmi.ValueSCTie) servant1;
    ValueSCImpl UseSSLSC = (ValueSCImpl) tsc1._delegate();
    syscond_objs[1] = UseSSLSC;
    PrimitiveSF UseSSLSF =
      new PrimitiveSF(UseSSLSC);

    com.bbn.quo.rmi.ValueSCTie tsc2 = (com.bbn.quo.rmi.ValueSCTie) servant2;
    ValueSCImpl trustSC = (ValueSCImpl) tsc2._delegate();
    syscond_objs[2] = trustSC;
    PrimitiveSF trustSF =
      new PrimitiveSF(trustSC);


    primitives = new PrimitiveSF[3];
    primitives[0] = BandwidthSF;
    primitives[0].sysCond.addObserver(this);
    primitives[1] = UseSSLSF;
    primitives[1].sysCond.addObserver(this);
    primitives[2] = trustSF;
    primitives[2].sysCond.addObserver(this);

    sequences = new SysCondSeqSF[0];

    // pre- and post- method initialization
    probes = new ProbeSCImpl[0];

    // top
    region = new Region("root", null);

    // Regions
    region.children = new Region[2];
    region.children[0] = new Region("SSL", region); // SSL
    region.children[0].idlEnum = 0;
    region.children[0].predicate =
      new DisjPairSF(
        UseSSLSF, 
        new LessThanSF(BandwidthSF, constInt10000000));

    // subregions of SSL
    region.children[0].children = null;

    region.children[1] = new Region("Normal", region); // Normal
    region.children[1].idlEnum = 1;
    region.children[1].predicate =
      constBooleantrue;

    // subregions of Normal
    region.children[1].children = null;

    // Region transitions
    // between transitions
    region.between = new Transition[0];

    // fromAny transitions
    region.fromAny = new Transition[0];

    // toAny transitions
    region.toAny = new Transition[0];

    // entering transitions
    region.entering = new Transition[0];

    // leaving transitions
    region.leaving = new Transition[0];

    kernel.addContract(this);
    waitForEval();
  }
}