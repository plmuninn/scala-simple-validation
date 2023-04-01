---
layout: docs
title: "Design"
permalink: design/
---
# Design

Library is design to be quite simple to understand. It's build from few simple components:
1. `ValidationSchemaContext`
2. `ValidationSchema`
3. `Field`
4. `FieldValidator`
5. `ValueValidator`
6. `InvalidField`

Composition of schema design:

![Schema design](https://mermaid.ink/img/pako:eNqVk7FugzAQhl_F8hSkZMDtxFCpalR165AoQ-MMJ3wUq2AjY5pWhHevIaACJQllML6f-787n3BJQy2QBjRK9DGMwViyXXNF3LMJY0zhSSuLX3a_g0QKsFKrgX4gq9XD6QWTLCdWE4GRVHhqvYuxyeuTa2evCEiVl92mGrdQi02p1xr_LDER_r55HS6mbo-6TWWD1LO7SXm0FsKYfJ771CZvDbtO8BfD2Osh2DwEu4L4rdNMY6RdGsuIP2Fl86z-39G6bwX2jj-MvduUZupDF5tDYbd6ufs_ZaKX-2lKvdIlTdGkIIW7D2WtcGrdX4WcBm4rwHxwylXl8qCwevOtQhpYU-CSFpmD4VrCu4GUBhEkuVMzUG9ad3H1AwmhM3U?type=png)](https://mermaid.live/edit#pako:eNqVk7FugzAQhl_F8hSkZMDtxFCpalR165AoQ-MMJ3wUq2AjY5pWhHevIaACJQllML6f-787n3BJQy2QBjRK9DGMwViyXXNF3LMJY0zhSSuLX3a_g0QKsFKrgX4gq9XD6QWTLCdWE4GRVHhqvYuxyeuTa2evCEiVl92mGrdQi02p1xr_LDER_r55HS6mbo-6TWWD1LO7SXm0FsKYfJ771CZvDbtO8BfD2Osh2DwEu4L4rdNMY6RdGsuIP2Fl86z-39G6bwX2jj-MvduUZupDF5tDYbd6ufs_ZaKX-2lKvdIlTdGkIIW7D2WtcGrdX4WcBm4rwHxwylXl8qCwevOtQhpYU-CSFpmD4VrCu4GUBhEkuVMzUG9ad3H1AwmhM3U "Schema design")


Result of schema looks like:
```
ValidationSchema[T] => NonEmptyList(T => Field[T] => NotEmptyList[FieldValidator]) => Combine => validateFn(T) => ValidationResult
```

