# Read Me
This is an attempt to write a Kotlin parsing library for the PF2e creature/item/spell/etc information published under https://github.com/foundryvtt/pf2e.  All entity data used for testing is copied from the aforementioned project and inherits its copyright.

Some attempt was made to scan the collated json code with an LLM, before this apporach prooved too erroneous to be relied on, so continuing development is a brute force process of throwing json at the parser until stuff sticks!

## Roadmap
A vague roadmap, to be expanded over time:
- [ ] Complete the parsing tree for each Pf2e entity type.
  - [ ] Items
  - [ ] Hazards
  - [ ] NPCs
  - [ ] Spells
- [ ] Get the parsing working as a generic function
- [ ] Handle rules elements to a degree
  - [ ] This is mostly checks and damage
- [ ] Write objects to the PF2e JSON format
  - [ ] Include rules element support where needed/possible
