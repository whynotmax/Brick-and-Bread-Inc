```plaintext
+---java
|   \---dev
|       \---mzcy
|           |   IdleEmpireMain.java
|           |   
|           +---bootstrap
|           |   |   IdleEmpireServer.java
|           |   |   
|           |   \---console
|           |           JLineConsole.java
|           |           
|           +---common
|           |   \---serialization
|           |       |   Serializer.java
|           |       |   Serializers.java
|           |       |   
|           |       +---exception
|           |       |       DeserializeException.java
|           |       |       SerializeException.java
|           |       |       
|           |       \---impl
|           |               ItemStackSerializer.java
|           |               
|           +---game
|           |   |   GameEngine.java
|           |   |   GameLoop.java
|           |   |   OfflineProgressCalculator.java
|           |   |   
|           |   +---colony
|           |   |   +---logic
|           |   |   |       ImmigrationManager.java
|           |   |   |       TaxPolicy.java
|           |   |   |       
|           |   |   \---model
|           |   |           Colonist.java
|           |   |           Colony.java
|           |   |           ColonyType.java
|           |   |           Profession.java
|           |   |           
|           |   +---economy
|           |   |   |   PriceCalculator.java
|           |   |   |   
|           |   |   +---logic
|           |   |   |       EconomyManager.java
|           |   |   |       
|           |   |   +---model
|           |   |   |       Stockpile.java
|           |   |   |       TradeRoute.java
|           |   |   |       
|           |   |   +---production
|           |   |   |       ProductionBuilding.java
|           |   |   |       ProductionBuildingType.java
|           |   |   |       ProductionRecipe.java
|           |   |   |       
|           |   |   \---resource
|           |   |           Resource.java
|           |   |           ResourceMarket.java
|           |   |           ResourceType.java
|           |   |           
|           |   +---empire
|           |   |       Empire.java
|           |   |       EmpireLevel.java
|           |   |       EmpireManager.java
|           |   |       EmpireStats.java
|           |   |       
|           |   +---event
|           |   |   |   RandomEventGenerator.java
|           |   |   |   
|           |   |   +---core
|           |   |   |       EventManager.java
|           |   |   |       EventType.java
|           |   |   |       GameEvent.java
|           |   |   |       
|           |   |   +---economic
|           |   |   |       EconomyEvent.java
|           |   |   |       
|           |   |   \---special
|           |   |           SpecialEvent.java
|           |   |           
|           |   +---finance
|           |   |       BankAccount.java
|           |   |       Company.java
|           |   |       CompanyType.java
|           |   |       DividendSystem.java
|           |   |       Investment.java
|           |   |       Stock.java
|           |   |       StockMarket.java
|           |   |       TransactionLog.java
|           |   |       
|           |   +---policy
|           |   |       CorruptionSystem.java
|           |   |       GovernmentType.java
|           |   |       LawSystem.java
|           |   |       Policy.java
|           |   |       PolicyManager.java
|           |   |       ReformSystem.java
|           |   |       TaxationPolicy.java
|           |   |       
|           |   +---research
|           |   |       BonusType.java
|           |   |       ResearchDoneEvent.java
|           |   |       ResearchManager.java
|           |   |       ResearchSlot.java
|           |   |       TechNode.java
|           |   |       TechTree.java
|           |   |       
|           |   +---tick
|           |   |       GameTick.java
|           |   |       Tickable.java
|           |   |       TickManager.java
|           |   |       TimeSpeed.java
|           |   |       
|           |   \---world
|           |           ClimateZone.java
|           |           Region.java
|           |           RegionController.java
|           |           TerrainType.java
|           |           WorldGenerator.java
|           |           WorldMap.java
|           |           
|           +---infrastructure
|           |   +---json
|           |   |   |   JsonSerializable.java
|           |   |   |   
|           |   |   +---exception
|           |   |   |       JsonException.java
|           |   |   |       
|           |   |   +---manager
|           |   |   |       JsonLoader.java
|           |   |   |       JsonManager.java
|           |   |   |       JsonSaver.java
|           |   |   |       
|           |   |   \---watcher
|           |   |           JsonWatcher.java
|           |   |           WatchedConfig.java
|           |   |           
|           |   \---mongo
|           |       +---codec
|           |       |       ComponentCodec.java
|           |       |       ItemStackCodec.java
|           |       |       PosCodec.java
|           |       |       
|           |       +---exception
|           |       |       DatabaseException.java
|           |       |       NoSuchRepositoryException.java
|           |       |       
|           |       +---manager
|           |       |       DatabaseManager.java
|           |       |       ProfileService.java
|           |       |       
|           |       +---profiles
|           |       |   |   IdlePlayerProfileService.java
|           |       |   |   
|           |       |   \---model
|           |       |           IdlePlayer.java
|           |       |           
|           |       \---repository
|           |               IdlePlayerRepository.java
|           |               
|           +---service
|           |       ColonyService.java
|           |       EconomyService.java
|           |       GameSaveService.java
|           |       ResearchService.java
|           |       
|           \---ui
|               +---colony
|               |       ColonyOverviewInterface.java
|               |       
|               +---common
|               |       StyleConstants.java
|               |       TooltipFactory.java
|               |       
|               +---empire
|               |       EmpireDashboard.java
|               |       
|               +---finance
|               |       InvestmentPanelInterface.java
|               |       
|               +---market
|               |       MarketInterface.java
|               |       
|               \---research
|                       ResearchInterface.java
|                       
\---resources
    |   logback.xml
    |   
    +---config
    |   |   balance_config.json
    |   |   game_settings.json
    |   |   
    |   \---language
    |           de_DE.json
    |           en_US.json
    |           
    +---data
    |       buildings.json
    |       policies.json
    |       resources.json
    |       tech_tree.json
    |       
    +---gui
    |   \---themes
    |           default_theme.json
    |           modern_theme.json
    |           retro_theme.json
    |           
    \---presets
        |   sandbox.json
        |   
        +---campaign
        |       campaign_01.json
        |       
        \---scenario
                001_capitalist_dreams.json
```