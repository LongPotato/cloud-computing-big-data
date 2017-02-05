## Entities

A citizenship system. Each citizen of a country can have a passport.
One country can have many citizen, and a citizin can only have one passport.

```
entity Country {
  name String
}

entity Citizen {
  name String,
  dob ZonedDateTime,
  gender String
}

entity Passport {
  id String,
  startDate ZonedDateTime,
  endDate ZonedDateTime
}

relationship OneToMany {
  Country{citizen} to Citizen{country}
}

relationship OneToOne {
  Citizen{passport} to Passport
}
```
