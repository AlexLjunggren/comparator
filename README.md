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
Comparator comparator = new Comparator<T>(object1, object2);
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
- clazz - Class containing the field that has a diff. This is useful for embedded objects.

## Embedded Objects ##

Objects other than boxed types and Strings can be marked as @Comparable.

```java
public class User {
    @Comparable
    private Address address;
}

public class Address {
    @Comparable
    private String city;
    private String state;
}
```

## TODO ##

Add iterable handling

## Dependencies ##

- reflection-utils

