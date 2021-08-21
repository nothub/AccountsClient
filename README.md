# McApiLib

This is an updated fork of [Mojang/AccountsClient](https://github.com/Mojang/AccountsClient), a Java library used to interact with the public API for handling Minecraft accounts and profiles at Mojang.

---

The library api is not stable and currently provides the following functionality:

|||
| --- | --- |
| Lookup multiple profiles by name | `ProfileRepository.findProfilesByNames(String...) -> Profile[]` |
| Lookup multiple profiles by name | `ProfileRepository.findProfilesByNames(List<String>) -> List<Profile>` |
| Lookup profile by name           | `ProfileRepository.findProfileByName(String) -> Profile` |
| Lookup profile by uuid           | `ProfileRepository.findProfileByUuid(String) -> Profile` |
| Lookup profile by uuid           | `ProfileRepository.findProfileByUuid(UUID) -> Profile` |
| Lookup skin, model & textures    | `Profile.getTextures() -> Textures` |
| Lookup name history by uuid      | `Profile.getNameHistory() -> List<Name>` |

The only documentation is the code and this readme.
(The projects namespace was changed to avoid collision.)

---

```nbtt
                       .     _///_,
                     .      / ` ' '>
                       )   o'  __/_'>
                      (   /  _/  )_\'>
                       ' "__/   /_/\_>
                           ____/_/_/_/
                          /,---, _/ /
                         ""  /_/_/_/
                            /_(_(_(_                 \
                           (   \_\_\\_               )\
                            \'__\_\_\_\__            ).\
                            //____|___\__)           )_/
                            |  _  \'___'_(           /'
                             \_ (-'\'___'_\      __,'_'
                             __) \  \\___(_   __/.__,'
                   b'ger  ,((,-,__\  '", __\_/. __,'
                                       '"./_._._-'
```

This code was provided as a courtesy by Mojang, feel free to build a client in another language to share with the community.
