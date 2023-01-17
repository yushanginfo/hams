package net.yushanginfo.hams.base.service

trait CodeService {
  def get[T](clazz: Class[T]): Seq[T]
}
