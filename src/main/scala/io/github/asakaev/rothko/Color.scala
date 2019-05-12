package io.github.asakaev.rothko

import cats.Eq

case class Color(v: Byte)

object Color {
  implicit val eqColor: Eq[Color] = Eq.fromUniversalEquals
}
