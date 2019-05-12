package io.github.asakaev.rothko

case class Pos(x: Int, y: Int) {
  def up: Pos    = copy(y = y - 1)
  def right: Pos = copy(x = x + 1)
  def down: Pos  = copy(y = y + 1)
  def left: Pos  = copy(x = x - 1)

  def neighbours: Set[Pos] = Set(up, right, down, left)
}
