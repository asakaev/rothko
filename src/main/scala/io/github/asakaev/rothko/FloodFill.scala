package io.github.asakaev.rothko

import cats.Eq
import fs2._

object FloodFill {

  type Canvas = Vector[Vector[Color]]

  def color(cv: Canvas, p: Pos): Option[Color] =
    for {
      row   <- cv.lift(p.y)
      color <- row.lift(p.x)
    } yield color

  def inside(cv: Canvas, p: Pos): Boolean =
    cv.isDefinedAt(p.y) && cv(p.y).isDefinedAt(p.x)

  def paint(cv: Canvas, p: Pos, c: Color): Canvas =
    if (inside(cv, p)) cv.updated(p.y, cv(p.y).updated(p.x, c))
    else cv

  def floodFill(canvas: Canvas, pos: Pos, target: Color): Canvas =
    color(canvas, pos) match {
      case Some(replacement) if Eq[Color].neqv(replacement, target) =>
        pos.neighbours
          .filter(color(canvas, _).exists(Eq[Color].eqv(_, replacement)))
          .foldLeft(paint(canvas, pos, target)) { (s, p) =>
            floodFill(s, p, target)
          }
      case _ => canvas
    }

  def streamFill(canvas: Canvas, pos: Pos, target: Color): Stream[Pure, Canvas] =
    Stream(color(canvas, pos)).unNone.flatMap { replacement =>
      Stream.unfold((canvas, List(pos))) {
        case (_, Nil) => None
        case (cv0, p :: stack) =>
          val cv = paint(cv0, p, target)
          val seeds = p.neighbours.toList.filter {
            color(cv, _).exists(c => Eq[Color].eqv(c, replacement) && Eq[Color].neqv(c, target))
          }
          Some(cv -> (cv -> (seeds ++ stack)))
      }
    }

}
