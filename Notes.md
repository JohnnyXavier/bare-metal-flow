# bare-metal-code **FLOW**

---

## Notes:

Entities:

- is System:
    - there will be default "dictionaries" values on the DB seeded by default that we will keep them during the lifetime of the app
    - these system values won't be deletable ie:
        - status of cards
        - types of cards
        - seniority
    - users can add more to the default values, or even modify properties, but not deleted them completely as their uuid will be needed   