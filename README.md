# KubeJSRecipeBlocker
動的にレシピを封印できるMod

## 対応Mod
- Minecraft
    - 作業台 (CraftingMenu)
    - かまど系統 (AbstractFurnaceBlockEntity)
    - 焚火 (CampfireBlockEntity)
    - 醸造台 (BrewingStandBlockEntity)
        - Event用レシピ「MinecraftContainerBrewingRecipe」「MinecraftPotionBrewingRecipe」を追加
    - 鍛冶台 (SmithingMenu)
    - ストーンカッター (StonecutterMenu)
- Advanced AE
    - 量子クラフター (QuantumCrafterEntity)
    - 反応室 (ReactionChamberEntity)
- AE2
    - チャージャー (ChargerBlockEntity)
    - 刻印機 (InscriberBlockEntity)
    - ワールドレシピ (TransformLogic)
    - エントロピーマニュピケーター (EntropyManipulatorItem)
    - クラフティングターミナル (CraftingTermMenuMixin)
    - 分子組立機 (MolecularAssemblerBlockEntity)
    - マターコンデンサー (CondenserBlockEntity)
        - Event用レシピ「AE2CondenserRecipe」を追加
- Apotheosis
    - エンチャント台 (ApothEnchantmentMenu)
    - 解体作業台 (SalvagingMenu)
    - ジェム加工台 (GemCuttingMenu)
        - Event用レシピ「ApotheosisGemCuttingRecipe」を追加
- Avaritia
    - スカルク,ネザー,エンド,エクストリーム作業台 (TierCraftMenu)
    - ニュートロニウム圧縮機 (NeutronCompressorTile)
    - エクストリーム鍛冶台 (ExtremeSmithingMenu)
- Extended AE
    - 拡張型チャージャー (TileExCharger)
    - 拡張型刻印機 (InscriberThread)
    - 拡張型分子組立機 (CraftingThread)
    - 回路スライサー (TileCircuitCutter)

## 対応予定Mod
- Chipped
- Create
- Create Slice & Dice
- Draconic Evolution
- Farmer's Delight
- Flux Networks
- FramedBlocks
- Iron's Spells 'n Spellbooks
- Mekanism
- ProjectE
- Rechiseled
- Sophisticated Backpacks
- Sophisticated Storage
- Timeless & Classics Guns: Zero
- Tinkers' Construct
- TofuCraftReload

## KubeJSの書き方
```js
RecipeBlockerEvents.blockRecipes((event) => {
    event.filter(data => {
      return 条件Boolean;
    });
    
    event.filter(レシピタイプId, data => {
      return 条件Boolean;
    });
});
```
returnがfalseだったらレシピ封印