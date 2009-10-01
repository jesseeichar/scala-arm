// -----------------------------------------------------------------------------
//
//  scala.arm - The Scala Incubator Project
//  Copyright (c) 2009 The Scala Incubator Project. All rights reserved.
//
//  The primary distribution site is http://jsuereth.github.com/scala-arm
//
//  This software is released under the terms of the Revised BSD License.
//  There is NO WARRANTY.  See the file LICENSE for the full text.
//
// -----------------------------------------------------------------------------

package scala.resource

import scala.collection.Traversable

/** 
 * This trait provides a means to ensure traversable access to items inside a resource, while ensuring that the
 * resource is opened/closed appropriately before/after the traversal.   
 */
trait ManagedTraversable[+A, H, B] extends Traversable[B] {
  /**
   * The resource we plan to traverse through  
   */
  val resource : ManagedResource[A,H]

  /**
   * This method gives us an iterator over items in a resource.                               
   */
  protected def iterator(v : H) : Iterator[B]


  /**
   * Executes a given function against all items in the resource.  The resource is opened/closed during the call
   * to this method.
   */
  def foreach[U](f: B => U): Unit = resource.acquireFor( r => iterator(r).foreach(f) )

}