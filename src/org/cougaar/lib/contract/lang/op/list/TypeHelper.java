/*
 * <copyright>
 * Copyright 1997-2001 Defense Advanced Research Projects
 * Agency (DARPA) and ALPINE (a BBN Technologies (BBN) and
 * Raytheon Systems Company (RSC) Consortium).
 * This software to be used only in accordance with the
 * COUGAAR licence agreement.
 * </copyright>
 */

package org.cougaar.lib.contract.lang.op.list;

import java.util.*;

import org.cougaar.lib.contract.lang.*;

/**
 **/
public final class TypeHelper {

  public static final int EXPECT_ARRAY       = 0;
  public static final int EXPECT_LIST        = 1;
  public static final int EXPECT_COLLECTION  = 2;
  public static final int EXPECT_ITERATOR    = 3;
  public static final int EXPECT_ENUMERATION = 4;
  public static final int EXPECT_UNKNOWN     = 5;

  public static final int getExpectedId(
      final OpParser p, 
      final boolean setToElementType) {
    // get expected list type
    TypeList sharedTypeList = p.getTypeList();
    List knownTypes = sharedTypeList.getKnownTypes();
    int nTypes = knownTypes.size();
    for (int i = 0; ; i++) {
      Type ti;
      if (i < nTypes) {
        // use next known type
        ti = (Type)knownTypes.get(i);
      } else if (i == nTypes) {
        // use assumed type
        ti = sharedTypeList.getWanted();
      } else {
        // no matching type found!
        return EXPECT_UNKNOWN;
      }
      if (!(ti.isNot())) {
        Class ticl = ti.getClazz();
        if (ticl.isArray()) {
          // elements are in Array
          if (setToElementType) {
            p.setTypeList(ticl.getComponentType());
          }
          return EXPECT_ARRAY;
        } else if (List.class.isAssignableFrom(ticl)) {
          // elements are in List
          if (setToElementType) {
            p.setTypeList(Object.class);
          }
          return EXPECT_LIST;
        } else if (Collection.class.isAssignableFrom(ticl)) {
          // elements are in a non-List Collection
          if (setToElementType) {
            p.setTypeList(Object.class);
          }
          return EXPECT_COLLECTION;
        } else if (Iterator.class.isAssignableFrom(ticl)) {
          // elements are in Iterator
          if (setToElementType) {
            p.setTypeList(Object.class);
          }
          return EXPECT_ITERATOR;
        } else if (Enumeration.class.isAssignableFrom(ticl)) {
          // elements are in Enumeration
          if (setToElementType) {
            p.setTypeList(Object.class);
          }
          return EXPECT_ENUMERATION;
        } else {
          // not a list
        }
      }
    }
  }
}
