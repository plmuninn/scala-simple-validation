package pl.muninn.simple.validation.validators

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait OptionValidators {
  def ifDefined[T](validators: NonEmptyList[ValueValidator[T]]): ValueValidator[Option[T]] = ValueValidator.instance[Option[T]] { (key, value) =>
    value.fold(valid)(value => validators.runAndCombine(key, value))
  }

  def ifDefined[T](validators: ValueValidator[T]): ValueValidator[Option[T]] =
    ifDefined[T](NonEmptyList.of(validators))

  def isDefined[T]: ValueValidator[Option[T]] = ValueValidator.instance { (key, value) =>
    if (value.isDefined) valid else invalid(InvalidField.EmptyField(key))
  }

  def notDefined[T]: ValueValidator[Option[T]] = ValueValidator.instance { (key, value) =>
    if (value.isEmpty) valid else invalid(InvalidField.ExpectedEmpty(key))
  }
}

object OptionValidators extends OptionValidators
