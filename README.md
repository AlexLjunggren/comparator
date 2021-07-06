## Comparator ##

Compare objects by annotated member variables. Non-annotated members will be ignored.

```java
public class User {
    @Comparable
    private String name;
}
```

Instantiate

```java
Comparator comparator = new Comparator(object1, object2);
```

Is Equal

```java
boolean equal = comparator.isEqual()
```

Differences

```java
List<Diff> diffs = comparator.compare();
```

Diff
- name - Field name
- value1 - value of object1 field
- value2 - value of object2 field

# Exceptions #

A runtime ComparatorException will be thrown if two object of different classes are being compared.
