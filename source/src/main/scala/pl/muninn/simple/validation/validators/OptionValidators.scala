package pl.muninn.simple.validation.validators

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator
import pl.muninn.simple.validation.ValueValidator.valid
import pl.muninn.simple.validation.validators.CommonValidators.EmptyMagnetic

trait OptionValidators {

  private implicit def optionEmptyMagnetic[T]: EmptyMagnetic[Option[T]] = _.isEmpty

  def defined[T]: ValueValidator[Option[T]] = CommonValidators.notEmpty

  def notDefined[T]: ValueValidator[Option[T]] = CommonValidators.empty

  def ifDefined[T](validators: NonEmptyList[ValueValidator[T]]): ValueValidator[Option[T]] = ValueValidator.instance[Option[T]] { (key, value) =>
    value.fold(valid)(value => validators.runAndCombine(key, value))
  }

  def ifDefined[T](validators: ValueValidator[T]): ValueValidator[Option[T]] =
    ifDefined[T](NonEmptyList.of(validators))
}

object OptionValidators extends OptionValidators
