// When the user clicks on the search box, we want to toggle the search dropdown
function displayToggleSearch(e) {
  e.preventDefault();
  e.stopPropagation();

  closeDropdownSearch(e);
  
  if (idx === null) {
    console.log("Building search index...");
    prepareIdxAndDocMap();
    console.log("Search index built.");
  }
  const dropdown = document.querySelector("#search-dropdown-content");
  if (dropdown) {
    if (!dropdown.classList.contains("show")) {
      dropdown.classList.add("show");
    }
    document.addEventListener("click", closeDropdownSearch);
    document.addEventListener("keydown", searchOnKeyDown);
    document.addEventListener("keyup", searchOnKeyUp);
  }
}

//We want to prepare the index only after clicking the search bar
var idx = null
const docMap = new Map()

function prepareIdxAndDocMap() {
  const docs = [  
    {
      "title": "About",
      "url": "/scala-simple-validation/docs/",
      "content": "About scala-simple-validation was designed to allow in simple way validate case classes and other data structures. It provides: easy way for describing validation schema few common validators to use simple way of creating your own, custom validators designing more complex validation process - where validation depends on some specific value Library was designed in a way to be easy to use and quite elastic. It was created because using cats Validated was really repetitive and other libraries too \"complicated\" in my opinion. I wanted to create something simple and easy to understand. Other validation libraries They are other validation libraries that should mention here - some of them were inspiration for this one: accord dupin octopus fields Dependencies Libraray is build using cats in version 2.8.0"
    } ,    
    {
      "title": "Any type validators",
      "url": "/scala-simple-validation/docs/validators/any/",
      "content": "Any type Validators Validators for Any type are : Name Usage in composition syntax Usage in implicit syntax Description Error codes Values equal equalValue(&lt;value&gt;) .equalValue(&lt;value&gt;) Runs validators on all collection elements and fails if any of them fails equal_field Custom validator customValid(&lt;error_code&gt;, &lt;error_reason&gt;, &lt;metadata&gt;)(&lt;function&gt;) .custom(&lt;code&gt;, &lt;reason&gt;, &lt;metadata&gt;)(&lt;function&gt;) Fails if function returns false N\\A Fields equal fieldsEqual .fieldsEqual Fails if tuple values is not equal fields_not_equal"
    } ,    
    {
      "title": "Collection validators",
      "url": "/scala-simple-validation/docs/validators/collection/",
      "content": "Collection Validators Validators for Collection are : Name Usage in composition syntax Usage in implicit syntax Description Error codes Runs on all elements all(&lt;validators&gt;) .all(&lt;validators&gt;) Runs validators on all collection elements and fails if any of them fails N\\A Is not empty notEmptyCollection .notEmpty Fails if collection is empty empty_field Is empty emptyCollection .empty Fails if collection is not empty empty_expected Minimal value minimalLengthCollection(8) .minimalLength(8) Fails if length of collection is greater or equal minimal value minimal_length Maximal value maximalLengthCollection(8) .maximumLength(8) Fails if length of collection is lower or equal maximal value maximal_length Exact length exactLengthCollection(8) .expectedLength(8) Fail if length of collection is not exactly same as defined in function expected_length Collection types Methods for collection comes in two versions - generic and specific. List above define generic validators. Specific one are defined using pattern of validation + [?In] + type. For example: emptyList or allInVector Definitions are provided for: List Seq Set Vector"
    } ,    
    {
      "title": "Compose validators",
      "url": "/scala-simple-validation/docs/usage/compose-validators/",
      "content": "Compose validators You can easily compose own validator using defined already validators. For example: import pl.muninn.simple.validation.all._ case class Field(name:String, otherField:String) val myValidString = notEmptyString and minimalLengthString(8) // myValidString: cats.data.NonEmptyList[pl.muninn.simple.validation.ValueValidator[String]] = NonEmptyList( // head = pl.muninn.simple.validation.ValueValidator$$anon$1@7626f193, // tail = List(pl.muninn.simple.validation.ValueValidator$$anon$1@57381f0) // ) val schema:Schema[Field] = createSchema { context =&gt; context.field(_.name).is(myValidString) + context.field(_.otherField).is(myValidString) } // schema: Schema[Field] = &lt;function1&gt; schema.validate(Field(\"\",\"\")) // res0: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Append( // leftNE = Append( // leftNE = Singleton(a = EmptyField(field = \"name\")), // rightNE = Singleton( // a = MinimalLength(field = \"name\", expected = 8, value = 0) // ) // ), // rightNE = Append( // leftNE = Singleton(a = EmptyField(field = \"otherField\")), // rightNE = Singleton( // a = MinimalLength(field = \"otherField\", expected = 8, value = 0) // ) // ) // ) // )"
    } ,    
    {
      "title": "Custom validations",
      "url": "/scala-simple-validation/docs/usage/custom/",
      "content": "Quick custom validators You can easily create custom validators on fly - it’s really helpful during working on new or custom validators import pl.muninn.simple.validation.all._ case class Field(name:String, otherField:String) val schema:Schema[Field] = createSchema { context =&gt; context.field(_.name) .custom(code = \"contains_test\", reason = \"Should contains test\")(_.contains(\"test\")) } // schema: Schema[Field] = &lt;function1&gt; schema.validate(Field(\"\",\"\")) // res0: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Singleton(a = pl.muninn.simple.validation.InvalidField$$anon$1@502dfe2b) // ) schema.validate(Field(\"test\",\"\")) // res1: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Valid( // a = () // ) Complex validation schema You can also easily create custom validation logic. For example, some values should be defined only if other value is set to specific value etc. import pl.muninn.simple.validation.all._ case class Product(name:String, price:Long) case class Order(totalPrice:Long, products:List[Product]) val orderSchema: Schema[Order] = createSchema { context =&gt; context.field(_.totalPrice).min(1) + context.field(_.products).notEmpty + context.custom { order =&gt; // If total price is equal or above 100 we want all products to be at least 50 if (order.totalPrice &gt;= 100) { context.field(_.products.map(_.price)).all(min(50L)) } else { // Don't validate anything - we are good context.noneValidator } } } // orderSchema: Schema[Order] = &lt;function1&gt; orderSchema.validate(Order(10, List.empty)) // res2: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Singleton(a = EmptyField(field = \"products\")) // ) orderSchema.validate( Order( 60, List( Product(\"My product\", 20), Product(\"My product\", 20), Product(\"My product\", 20), ) ) ) // res3: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Valid( // a = () // ) orderSchema.validate( Order( 100, List( Product(\"My product\", 20), Product(\"My product\", 20), Product(\"My product\", 20), Product(\"My product\", 20), Product(\"My product\", 20), ) ) ) // res4: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Append( // leftNE = Append( // leftNE = Append( // leftNE = Append( // leftNE = Singleton( // a = MinimalValue( // field = \"products.price.0\", // expected = 50L, // value = 20L // ) // ), // rightNE = Singleton( // a = MinimalValue( // field = \"products.price.1\", // expected = 50L, // value = 20L // ) // ) // ), // rightNE = Singleton( // a = MinimalValue( // field = \"products.price.2\", // expected = 50L, // value = 20L // ) // ) // ), // rightNE = Singleton( // a = MinimalValue( // field = \"products.price.3\", // expected = 50L, // value = 20L // ) // ) // ), // rightNE = Singleton( // a = MinimalValue(field = \"products.price.4\", expected = 50L, value = 20L) // ) // ) // ) orderSchema.validate( Order( 165, List( Product(\"My product\", 55), Product(\"My product\", 55), Product(\"My product\", 55), ) ) ) // res5: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Valid( // a = () // ) Custom validators You can define full own validators and errors import pl.muninn.simple.validation.all._ import pl.muninn.simple.validation.InvalidField import pl.muninn.simple.validation.ValueValidator case class MyError(field:String) extends InvalidField { override def reason: String = \"Because I think this filed is invalid\" override def code: String = \"error_code\" } val myOwnValidator:ValueValidator[String] = ValueValidator.instance { case (key, value) =&gt; if (value.contains(\"not error\")) valid else invalid(MyError(key)) } // myOwnValidator: ValueValidator[String] = pl.muninn.simple.validation.ValueValidator$$anon$1@4e1ae9bc val customValidatorSchema:Schema[Field] = createSchema { context =&gt; context.field(_.name).is(myOwnValidator) } // customValidatorSchema: Schema[Field] = &lt;function1&gt; customValidatorSchema.validate(Field(\"error\",\"\")) // res6: cats.data.package.ValidatedNec[InvalidField, Unit] = Invalid( // e = Singleton(a = MyError(field = \"name\")) // ) customValidatorSchema.validate(Field(\"not error\",\"\")) // res7: cats.data.package.ValidatedNec[InvalidField, Unit] = Valid(a = ())"
    } ,    
    {
      "title": "Errors",
      "url": "/scala-simple-validation/docs/errors/",
      "content": "Errors Error structure is reperesented by simple trait: trait InvalidField { def field: String def reason: String def code: String def metadata: Map[String, String] = Map.empty } Where each filed represents: field - name of validated field reason - descriptive reason of failure code - code of error metadata - list of meta information of error ex. when number was expected to be lower then 10 you will find there what was expected value List of error codes Here is build in list of errors codes with descriptions: Code Description Data types equal_field Value was not equal expected value any empty_field Value was expected to be not empty stringoptioncollection empty_expected Value was expected to be empty stringoptioncollection email_field Value was expected to be email string min_count_symbols Value was expected contains count of symbols string min_count_digits Value was expected contains count of digits string min_count_lower_case Value was contains count of lower case characters string min_count_upper_case Value was expected count of upper case characters string fields_not_equal Two fields were not equal tuple minimal_value Value was expected to greater or equal minimal value number maximal_value Value was expected to lower or equal maximal value number minimal_length Value was expected to have length greater or equal minimal value stringcollection maximal_length Value was expected to have length lower or equal maximal value stringcollection expected_length Value was expected to exact length stringcollection key_missing Value was expected to have specific key map keys_missing Value was expected to have specific list of keys map"
    } ,    
    {
      "title": "Field names",
      "url": "/scala-simple-validation/docs/usage/field-names/",
      "content": "Field names Field name can be set or retrieved using macro: import pl.muninn.simple.validation.all._ case class Field(name:String, otherField:String) val macroSchema:Schema[Field] = createSchema { context =&gt; context.field(_.name).notEmpty + context.field(_.otherField).notEmpty } // macroSchema: Schema[Field] = &lt;function1&gt; macroSchema.validate(Field(\"\",\"\")) // res0: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Append( // leftNE = Singleton(a = EmptyField(field = \"name\")), // rightNE = Singleton(a = EmptyField(field = \"otherField\")) // ) // ) val customNameSchema:Schema[Field] = createSchema { context =&gt; context.field(\"name\")(_.name).notEmpty + context.field(\"myName\")(_.otherField).notEmpty } // customNameSchema: Schema[Field] = &lt;function1&gt; customNameSchema.validate(Field(\"\",\"\")) // res1: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Append( // leftNE = Singleton(a = EmptyField(field = \"name\")), // rightNE = Singleton(a = EmptyField(field = \"myName\")) // ) // ) Macro names Macro design for retrieving value name was done in a way to allow user get complex name in easy way. For example: import pl.muninn.simple.validation.all._ case class ComplexOtherField(otherField:String) case class ComplexField(field:Option[ComplexOtherField]) val schema:Schema[ComplexField] = createSchema { context =&gt; // field name will be `field.otherField` context.field(_.field.map(_.otherField)).definedAnd(notEmptyString) } // schema: Schema[ComplexField] = &lt;function1&gt; schema.validate(ComplexField(None)) // res2: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Singleton(a = EmptyField(field = \"field.otherField\")) // ) schema.validate(ComplexField(Some(ComplexOtherField(\"value\")))) // res3: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Valid( // a = () // )"
    } ,    
    {
      "title": "Quickstart",
      "url": "/scala-simple-validation/",
      "content": "About scala-simple-validation was designed to allow in simple way validate case classes and other data structures. It provides: easy way for describing validation schema few common validators to use simple way of creating your own, custom validators designing more complex validation process - where validation depends on some specific value Getting started Add to yours build.sbt: resolvers ++= Seq( Resolver.sonatypeRepo(\"releases\"), Resolver.sonatypeRepo(\"snapshots\") ) libraryDependencies += \"pl.muninn\" %% \"scala-simple-validation\" % \"0.0.4\" Then you need to only add in your code: import pl.muninn.simple.validation.all._ And you can start using it Usage example Simple example of how to use library import pl.muninn.simple.validation.all._ case class LoginRequest(login:String, password:String) val schema:Schema[LoginRequest] = createSchema { context =&gt; context.field(_.login).notEmpty + context.field(_.password).notEmpty } // schema: Schema[LoginRequest] = &lt;function1&gt; val result = schema.validate(LoginRequest(\"admin\", \"admin\")) // result: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Valid( // a = () // ) result.isValid // res0: Boolean = true"
    } ,    
    {
      "title": "Install",
      "url": "/scala-simple-validation/docs/install/",
      "content": "To install library add to your build.sbt file: resolvers ++= Seq( Resolver.sonatypeRepo(\"releases\"), Resolver.sonatypeRepo(\"snapshots\") ) libraryDependencies += \"pl.muninn\" %% \"scala-simple-validation\" % \"0.0.4\" Support for ScalaJS and ScalaNative Library is published as ScalaJS and ScalaNative libraries too - to use it just add to your build.sbt file: resolvers ++= Seq( Resolver.sonatypeRepo(\"releases\"), Resolver.sonatypeRepo(\"snapshots\") ) libraryDependencies += \"pl.muninn\" %%% \"scala-simple-validation\" % \"0.0.4\""
    } ,      
    {
      "title": "Map validators",
      "url": "/scala-simple-validation/docs/validators/map/",
      "content": "Map Validators Validators for Map are : Name Usage in composition syntax Usage in implicit syntax Description Error codes Contains key containsKey(\"key\") .containsKey(\"key\") Fails if map doesn’t contains key key_missing Contains Keys containsKeys(List(\"key1\", \"key2\")) .containsKeys(List(\"key1\", \"key2\")) Fails if map doesn’t contains all keys key_missing keys_missing"
    } ,    
    {
      "title": "Number validators",
      "url": "/scala-simple-validation/docs/validators/number/",
      "content": "Number Validators Validators for Number are : Name Usage in composition syntax Usage in implicit syntax Description Error codes Minimal value min(8) .min(8) Fails if value is greater or equal minimal value minimal_value Maximal value max(8) .max(8) Fails if value is lower or equal maximal value maximal_value Equal value equalNumber(8) .equal(8) Fail if value is not exactly same value defined in function equal_field"
    } ,    
    {
      "title": "Option validators",
      "url": "/scala-simple-validation/docs/validators/option/",
      "content": "Option Validators Validators for Option are : Name Usage in composition syntax Usage in implicit syntax Description Error codes When value is defined ifDefined(&lt;validators&gt;) .ifDefined(&lt;validators&gt;) Runs passed to it validators when value is defined N\\A Is defined and N\\A .definedAnd(&lt;validators&gt;) or .notEmptyAnd(&lt;validators&gt;) Fails if value is not defined and runs passed to it validators if value is defined empty_field If is defined defined .notEmpty or .isDefined Fails if value is not defined empty_field If is not defined notDefined .empty or .notDefined Fails if value is defined empty_expected"
    } ,    
    {
      "title": "Schema definitions",
      "url": "/scala-simple-validation/docs/usage/schema/",
      "content": "Schema definitions You can create validation schema using composition or using implicits import pl.muninn.simple.validation.all._ case class Field(name:String, otherField:String) val compositionSchema:Schema[Field] = createSchema { context =&gt; context.field(_.name).is(notEmptyString and minimalLengthString(8)) + context.field(_.otherField).is(notEmptyString) } // compositionSchema: Schema[Field] = &lt;function1&gt; compositionSchema.validate(Field(\"\",\"\")) // res0: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Append( // leftNE = Append( // leftNE = Singleton(a = EmptyField(field = \"name\")), // rightNE = Singleton( // a = MinimalLength(field = \"name\", expected = 8, value = 0) // ) // ), // rightNE = Singleton(a = EmptyField(field = \"otherField\")) // ) // ) val implicitSchema:Schema[Field] = createSchema { context =&gt; (context.field(_.name).notEmpty and minimalLengthString(8)) + context.field(_.otherField).notEmpty } // implicitSchema: Schema[Field] = &lt;function1&gt; implicitSchema.validate(Field(\"\",\"\")) // res1: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Invalid( // e = Append( // leftNE = Append( // leftNE = Singleton(a = EmptyField(field = \"name\")), // rightNE = Singleton( // a = MinimalLength(field = \"name\", expected = 8, value = 0) // ) // ), // rightNE = Singleton(a = EmptyField(field = \"otherField\")) // ) // )"
    } ,      
    {
      "title": "String validators",
      "url": "/scala-simple-validation/docs/validators/string/",
      "content": "String Validators Validators for String are : Name Usage in composition syntax Usage in implicit syntax Description Error codes Empty string emptyString .empty Fails if string is not empty empty_expected None empty string notEmpty .notEmptyString Fails if string is empty empty_field Email email .email Fails if string is not email email_field Password password() .password Fails if string is not at least 8 characters long and not contains 1 number and 1 special symbol and big and small letters minimal_length min_count_symbols min_count_digits min_count_lower_case min_count_upper_case Minimal length minimalLengthString(8) .minimalLength(8) Fails if length is greater or equal minimal value minimal_length Maximal length maximalLengthString(8) .maximalLength(8) Fails if length is lower or equal maximal value maximal_length Expected length exactLengthString(8) .exactLength(8) Fail if length is not exactly same value defined in function expected_length Contains symbols minimalCountSymbols(1, &lt;list of symbols&gt;) .containsSymbols(1, &lt;list of symbols&gt;) Fail if count of symbols is lower of minimal value min_count_symbols Contains digits minimalCountDigits(1) .containsDigits(1) Fail if count of digits is lower of minimal value min_count_digits Contains lower case characters minimalCountLowerCases(1) .containsLowerCase(1) Fail if count of lower case characters is lower of minimal value min_count_lower_case Contains upper case characters minimalCountUpperCases(1) .containsUpperCase(1) Fail if count of upper case characters is lower of minimal value min_count_upper_case"
    } ,    
    {
      "title": "Usage",
      "url": "/scala-simple-validation/docs/usage/",
      "content": "Usage example Simple example of how to use library import pl.muninn.simple.validation.all._ case class LoginRequest(login:String, password:String) val schema:Schema[LoginRequest] = createSchema { context =&gt; context.field(_.login).notEmpty + context.field(_.password).notEmpty } // schema: Schema[LoginRequest] = &lt;function1&gt; val result = schema.validate(LoginRequest(\"admin\", \"admin\")) // result: cats.data.package.ValidatedNec[pl.muninn.simple.validation.InvalidField, Unit] = Valid( // a = () // ) result.isValid // res0: Boolean = true Library provides Library allows to use two different ways of creating validation schema . It also provides simple way of defining custom validators and complex validation scenarios change field names compose validators"
    } ,    
    {
      "title": "Validators",
      "url": "/scala-simple-validation/docs/validators/",
      "content": "Validators Library provides set of build in validators for types: Any type values Optional values Strings Numbers Collections Maps"
    }    
  ];

  idx = lunr(function () {
    this.ref("title");
    this.field("content");

    docs.forEach(function (doc) {
      this.add(doc);
    }, this);
  });

  docs.forEach(function (doc) {
    docMap.set(doc.title, doc.url);
  });
}

// The onkeypress handler for search functionality
function searchOnKeyDown(e) {
  const keyCode = e.keyCode;
  const parent = e.target.parentElement;
  const isSearchBar = e.target.id === "search-bar";
  const isSearchResult = parent ? parent.id.startsWith("result-") : false;
  const isSearchBarOrResult = isSearchBar || isSearchResult;

  if (keyCode === 40 && isSearchBarOrResult) {
    // On 'down', try to navigate down the search results
    e.preventDefault();
    e.stopPropagation();
    selectDown(e);
  } else if (keyCode === 38 && isSearchBarOrResult) {
    // On 'up', try to navigate up the search results
    e.preventDefault();
    e.stopPropagation();
    selectUp(e);
  } else if (keyCode === 27 && isSearchBarOrResult) {
    // On 'ESC', close the search dropdown
    e.preventDefault();
    e.stopPropagation();
    closeDropdownSearch(e);
  }
}

// Search is only done on key-up so that the search terms are properly propagated
function searchOnKeyUp(e) {
  // Filter out up, down, esc keys
  const keyCode = e.keyCode;
  const cannotBe = [40, 38, 27];
  const isSearchBar = e.target.id === "search-bar";
  const keyIsNotWrong = !cannotBe.includes(keyCode);
  if (isSearchBar && keyIsNotWrong) {
    // Try to run a search
    runSearch(e);
  }
}

// Move the cursor up the search list
function selectUp(e) {
  if (e.target.parentElement.id.startsWith("result-")) {
    const index = parseInt(e.target.parentElement.id.substring(7));
    if (!isNaN(index) && (index > 0)) {
      const nextIndexStr = "result-" + (index - 1);
      const querySel = "li[id$='" + nextIndexStr + "'";
      const nextResult = document.querySelector(querySel);
      if (nextResult) {
        nextResult.firstChild.focus();
      }
    }
  }
}

// Move the cursor down the search list
function selectDown(e) {
  if (e.target.id === "search-bar") {
    const firstResult = document.querySelector("li[id$='result-0']");
    if (firstResult) {
      firstResult.firstChild.focus();
    }
  } else if (e.target.parentElement.id.startsWith("result-")) {
    const index = parseInt(e.target.parentElement.id.substring(7));
    if (!isNaN(index)) {
      const nextIndexStr = "result-" + (index + 1);
      const querySel = "li[id$='" + nextIndexStr + "'";
      const nextResult = document.querySelector(querySel);
      if (nextResult) {
        nextResult.firstChild.focus();
      }
    }
  }
}

// Search for whatever the user has typed so far
function runSearch(e) {
  if (e.target.value === "") {
    // On empty string, remove all search results
    // Otherwise this may show all results as everything is a "match"
    applySearchResults([]);
  } else {
    const tokens = e.target.value.split(" ");
    const moddedTokens = tokens.map(function (token) {
      // "*" + token + "*"
      return token;
    })
    const searchTerm = moddedTokens.join(" ");
    const searchResults = idx.search(searchTerm);
    const mapResults = searchResults.map(function (result) {
      const resultUrl = docMap.get(result.ref);
      return { name: result.ref, url: resultUrl };
    })

    applySearchResults(mapResults);
  }

}

// After a search, modify the search dropdown to contain the search results
function applySearchResults(results) {
  const dropdown = document.querySelector("div[id$='search-dropdown'] > .dropdown-content.show");
  if (dropdown) {
    //Remove each child
    while (dropdown.firstChild) {
      dropdown.removeChild(dropdown.firstChild);
    }

    //Add each result as an element in the list
    results.forEach(function (result, i) {
      const elem = document.createElement("li");
      elem.setAttribute("class", "dropdown-item");
      elem.setAttribute("id", "result-" + i);

      const elemLink = document.createElement("a");
      elemLink.setAttribute("title", result.name);
      elemLink.setAttribute("href", result.url);
      elemLink.setAttribute("class", "dropdown-item-link");

      const elemLinkText = document.createElement("span");
      elemLinkText.setAttribute("class", "dropdown-item-link-text");
      elemLinkText.innerHTML = result.name;

      elemLink.appendChild(elemLinkText);
      elem.appendChild(elemLink);
      dropdown.appendChild(elem);
    });
  }
}

// Close the dropdown if the user clicks (only) outside of it
function closeDropdownSearch(e) {
  // Check if where we're clicking is the search dropdown
  if (e.target.id !== "search-bar") {
    const dropdown = document.querySelector("div[id$='search-dropdown'] > .dropdown-content.show");
    if (dropdown) {
      dropdown.classList.remove("show");
      document.documentElement.removeEventListener("click", closeDropdownSearch);
    }
  }
}
