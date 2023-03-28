package pl.muninn.simple.validation.validator.typed

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValidationImplicits._
import pl.muninn.simple.validation.validator.ValueValidator
import pl.muninn.simple.validation.validator.ValueValidator.valid
import pl.muninn.simple.validation.validator.typed.CommonValidators.EmptyMagnetic

trait OptionValidators {

  private implicit def optionEmptyMagnetic[T]: EmptyMagnetic[Option[T]] = _.isEmpty

  def defined[T]: ValueValidator[Option[T]] = CommonValidators.notEmpty[Option[T]]

  def notDefined[T]: ValueValidator[Option[T]] = CommonValidators.empty[Option[T]]

  def ifDefined[T](validators: NonEmptyList[ValueValidator[T]]): ValueValidator[Option[T]] = ValueValidator.instance[Option[T]] { (key, value) =>
    value.fold(valid)(value => validators.runAndCombine(key, value))
  }

  def ifDefined[T](validators: ValueValidator[T]): ValueValidator[Option[T]] =
    ifDefined[T](NonEmptyList.of(validators))
}

object OptionValidators extends OptionValidators
