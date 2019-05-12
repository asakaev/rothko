package io.github.asakaev.rothko

import org.scalatest.FunSuite

class PosSuite extends FunSuite {
  test("neighbours") {
    assert(Pos(0, 0).neighbours == Set(Pos(0, -1), Pos(1, 0), Pos(0, 1), Pos(-1, 0)))
  }

}
