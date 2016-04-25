import scala.collection.immutable.BitSet
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.immutable.Queue
import scala.collection.mutable.TreeSet
import scala.collection.mutable.MutableList

/*
 * First we need to enumerate {a0,a1,a2,a3,a4,a5} and {b0,b1,b2,b3,b4,b5} while ignoring symmetry and only considering ordered sets
 * At each step, when we have a_i and b_i, we enumerate over all remaining square pairs we haven't fulfilled yet. 
 * 
 */

object Problem90 extends App {

  val mq = List(new SP(0, 1), new SP(0, 4), new SP(0, 9), new SP(1, 6), new SP(2, 5), new SP(3, 6), new SP(4, 9), new SP(6, 4), new SP(8, 1))
  var count = 0

  def solve(block1: BitSet, cur1: Int, block2: BitSet, cur2: Int, q: List[SP], lt: Boolean): Unit = {

    if (block1.size < 6) {
      for (i <- cur1 to block1.size + 4) {
        val lowerBound = if (lt) cur2 else cur2.max(i)
        for (j <- lowerBound to block2.size + 4) {
          solve(block1 + i, i + 1, block2 + j, j + 1, q.map(sp => sp.addFirst(i).addSecond(j)).filter(sp => !sp.satisfied), lt || i < j)
        }
      }
    }

    if (block1.size == 6 && q.isEmpty) {
      count += 1
    }
  }
  val start = System.currentTimeMillis()
  solve(BitSet(), 0, BitSet(), 0, mq, false)
  println(System.currentTimeMillis() - start)
  println(count)

  /*
   * Data structure that provides the following behaviour:
   * Two "boxes" that can contain up to two numbers each
   * Given two "constraints" a,b the data structure is considered satisfied if a is in box1 and b is in box2 or b is in box1 and a is in box2
   * 
   */
  class SP(val a: Int, val b: Int, val found: BitSet, val satisfied: Boolean) {

    //BitSet (first block has a, first block has b, second block has a, second block has b)
    def this(a: Int, b: Int) {
      this(a, b, BitSet(), false)
    }

    def addFirst(candidate: Int): SP = {
      if (valid(candidate, a)) {
        new SP(a, b, found + 0, satisfied || found(3))
      } else if (valid(candidate, b)) {
        new SP(a, b, found + 1, satisfied || found(2))
      } else {
        this
      }
    }

    def addSecond(candidate: Int): SP = {
      if (valid(candidate, a)) {
        new SP(a, b, found + 2, satisfied || found(1))
      } else if (valid(candidate, b)) {
        new SP(a, b, found + 3, satisfied || found(0))
      } else {
        this
      }
    }
    def valid(candidate: Int, against: Int): Boolean = {
      candidate == against || (against == 6 && candidate == 9) || (against == 9 && candidate == 6)
    }
  }

}