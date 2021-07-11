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

This is a Java client library used to interact with the public API for handling Minecraft accounts and profiles at Mojang.

This is an updated fork of the [original project](https://github.com/Mojang/AccountsClient).

The library api is not stable and currently provides the following functionality:

|||
| --- | --- |
| Lookup multiple profiles by name | `Profile[] findProfilesByNames(String...)`        |
| Lookup multiple profiles by name | `List<Profile> findProfilesByNames(List<String>)` |
| Lookup profile by name | `Profile findProfileByName(String)`               |
| Lookup profile by uuid | `Profile findProfileByUuid(String)`               |
| Lookup profile by uuid | `Profile findProfileByUuid(UUID)`                 |
| Lookup skin & cape by name | `Profile findProfileWithProperties(String)`       |
| Lookup skin & cape by uuid | `Profile findProfileWithProperties(UUID)`         |

(The projects namespace was changed to avoid collision.)

---

The only documentation is the code, and if you find something particularly weird it's probably a combination between not wanting to introduce unnecessary dependencies to Minecraft and not going overkill.

This code was provided as a courtesy by Mojang, feel free to build a client in another language to share with the community.
