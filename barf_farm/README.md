# Barf Script by Bill
## Usage Guide:
- Do bounty hunting and stuffs first
- Don't use barf mountain ticket, script should handle it
- Assumes you have your own custom combat script
- Assumes you have your own barf outfit, under `outfit barf`
- Does not do RO for you, create option for this later.
- Assumes you are using pantogram setup of <porquoise, ten-leaf clover, moxie, stench and bubblin' crude for MP regen>. If you are not, simply adjust the script to do so by editing the ash file. Also assumes you already have the items listed, otherwise enable `set barf_purchase = true`, which will enable a purchase of a porquoise. Up to you to get the rest, since I assume you already have a disassembled clover. Also assumes clover comes pre-disassembled.
- Assumes you have Ezandora's wonderful briefcase script if you have the briefcase
- Assumes you have Ezandora's FarFuture script if you have the time-spinner

## Settings:
This will run initially to set, but if you have to change it:
```javascript
set <property> = <true/false>
```

### Setting options:
- `barf_treeshake <true/false>` determines if you want to shake the tea tree.
- `barf_treeoption <voraci/sobrie/royal>` determines which tea you want. This is set automatically to royal, so change settings accordingly. No other teas are implemented because they not profitable :P.
- `barf_purchase <true/false>` will automatically purchase items for you if enables. Currently for pantogram only.
- If `barf_consume <true/false>` will decide whether to consume a barf diet. Defaults to hobo diet. Assumes you have the required foods.
