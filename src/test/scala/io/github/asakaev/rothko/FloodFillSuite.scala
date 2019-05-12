package io.github.asakaev.rothko

import io.github.asakaev.rothko.FloodFill._
import org.scalatest.FunSuite

class FloodFillSuite extends FunSuite {
  test("color") {
    assert(color(Vector(Vector(Color(42))), Pos(0, 0)) == Some(Color(42)))
  }

  test("paint") {
    val cv =
      Vector(
        Vector(Color(0), Color(1)),
        Vector(Color(2), Color(3))
      )

    val expected =
      Vector(
        Vector(Color(0), Color(1)),
        Vector(Color(42), Color(3))
      )

    assert(paint(cv, Pos(0, 1), Color(42)) == expected)
  }

  test("floodFill") {
    val cv =
      Vector(
        Vector(Color(1), Color(1), Color(0)),
        Vector(Color(0), Color(1), Color(0)),
        Vector(Color(0), Color(0), Color(1))
      )

    val p = Pos(0, 0)
    val c = Color(42)

    val expected =
      Vector(
        Vector(Color(42), Color(42), Color(0)),
        Vector(Color(0), Color(42), Color(0)),
        Vector(Color(0), Color(0), Color(1))
      )

    assert(floodFill(cv, p, c) == expected)
  }

  test("streamFill") {
    val cv =
      Vector(
        Vector(Color(1), Color(1), Color(0)),
        Vector(Color(0), Color(1), Color(0)),
        Vector(Color(0), Color(0), Color(1))
      )

    val p = Pos(0, 0)
    val c = Color(42)

    val expected =
      Vector(
        Vector(Color(42), Color(42), Color(0)),
        Vector(Color(0), Color(42), Color(0)),
        Vector(Color(0), Color(0), Color(1))
      )

    assert(streamFill(cv, p, c).compile.last == Some(expected))
  }
}
