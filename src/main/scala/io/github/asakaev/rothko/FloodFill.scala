package io.github.asakaev.rothko

import cats.Eq

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

  def floodFill(cv: Canvas, p: Pos, tc: Color): Canvas =
    color(cv, p) match {
      case Some(cc) if Eq[Color].neqv(cc, tc) =>
        p.neighbours
          .filter(color(cv, _).exists(Eq[Color].eqv(_, cc)))
          .foldLeft(paint(cv, p, tc)) { (s, p) =>
            floodFill(s, p, tc)
          }
      case _ => cv
    }

}
