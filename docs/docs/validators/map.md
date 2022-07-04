---
layout: docs
title: "Map validators"
permalink: docs/validators/map/
---
# Map Validators

Validators for **Map** are :

| **Name**      | **Usage in composition syntax**      | **Usage in implicit syntax**          | **Description**                        | **Error codes**              |
|---------------|--------------------------------------|---------------------------------------|----------------------------------------|------------------------------|
| Contains key  | `containsKey("key")`                 | `.containsKey("key")`                 | Fails if map doesn't contains key      | `key_missing`                |
| Contains Keys | `containsKeys(List("key1", "key2"))` | `.containsKeys(List("key1", "key2"))` | Fails if map doesn't contains all keys | `key_missing` `keys_missing` |
