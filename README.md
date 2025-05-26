# Brick-and-Bread-Inc
🧠 Wirtschaftliches Kolonie-Idle-Game, mit Kapitalismus-Flair. Siedler trifft auf Wall Street – in Minecraft, aber mit Automation und AFK-Fortschritt.

## File tree
```
src/
└── main/
    └── java/
        └── dev/
            └── max/
                └── idleempire/
                    ├── IdleEmpireMain.java                  <-- Einstiegspunkt
                    │
                    ├── bootstrap/                           <-- Setup & Initialisierung
                    │   ├── EmpireBootstrap.java
                    │   ├── ModuleLoader.java
                    │   └── ResourcePreloader.java
                    │
                    ├── game/                                <-- Spielzustand & Management
                    │   ├── GameEngine.java
                    │   ├── GameLoop.java
                    │   ├── GameState.java
                    │   └── OfflineProgressCalculator.java
                    │
                    ├── empire/                              <-- Spielerimperien
                    │   ├── Empire.java
                    │   ├── EmpireManager.java
                    │   ├── EmpireStats.java
                    │   ├── EmpireLevel.java
                    │   ├── PrestigeSystem.java
                    │   └── EmpireSaveData.java
                    │
                    ├── colony/                              <-- Kolonien & Bevölkerung
                    │   ├── Colony.java
                    │   ├── ColonyType.java
                    │   ├── Colonist.java
                    │   ├── Profession.java
                    │   ├── HappinessSystem.java
                    │   ├── TaxPolicy.java
                    │   ├── EducationSystem.java
                    │   └── ImmigrationManager.java
                    │
                    ├── economy/                             <-- Wirtschaft & Produktion
                    │   ├── EconomyManager.java
                    │   ├── Resource.java
                    │   ├── ResourceType.java
                    │   ├── ProductionBuilding.java
                    │   ├── BuildingType.java
                    │   ├── ProductionRecipe.java
                    │   ├── ResourceMarket.java
                    │   ├── PriceCalculator.java
                    │   ├── TradeRoute.java
                    │   └── Stockpile.java
                    │
                    ├── finance/                             <-- Kapital & Finanzen
                    │   ├── BankAccount.java
                    │   ├── Investment.java
                    │   ├── StockMarket.java
                    │   ├── Stock.java
                    │   ├── Company.java
                    │   ├── CompanyType.java
                    │   ├── DividendSystem.java
                    │   └── TransactionLog.java
                    │
                    ├── research/                            <-- Technologien & Forschung
                    │   ├── TechTree.java
                    │   ├── TechNode.java
                    │   ├── ResearchSlot.java
                    │   ├── ResearchManager.java
                    │   ├── BonusType.java
                    │   └── DiscoveryEvent.java
                    │
                    ├── policy/                              <-- Politik & Verwaltung
                    │   ├── Policy.java
                    │   ├── PolicyManager.java
                    │   ├── TaxationPolicy.java
                    │   ├── LawSystem.java
                    │   ├── GovernmentType.java
                    │   ├── ReformSystem.java
                    │   └── CorruptionSystem.java
                    │
                    ├── event/                               <-- Spiel-Events
                    │   ├── GameEvent.java
                    │   ├── EventType.java
                    │   ├── EventManager.java
                    │   ├── RandomEventGenerator.java
                    │   ├── SpecialEvent.java
                    │   └── EconomicShockEvent.java
                    │
                    ├── world/                               <-- Weltkarte & Regionen
                    │   ├── WorldMap.java
                    │   ├── Region.java
                    │   ├── TerrainType.java
                    │   ├── ClimateZone.java
                    │   ├── WorldGenerator.java
                    │   └── RegionControl.java
                    │
                    ├── tick/                                <-- Zeitsteuerung
                    │   ├── GameTick.java
                    │   ├── TickManager.java
                    │   ├── Tickable.java
                    │   └── TimeSpeed.java
                    │
                    ├── player/                              <-- Spielerintegration
                    │   ├── EmpirePlayer.java
                    │   ├── PlayerManager.java
                    │   ├── PlayerStats.java
                    │   ├── PlayerPermissions.java
                    │   └── LoginRewardSystem.java
                    │
                    ├── ui/                                  <-- GUI & Menüs
                    │   ├── EmpireDashboard.java
                    │   ├── ColonyOverviewGUI.java
                    │   ├── MarketGUI.java
                    │   ├── ResearchGUI.java
                    │   ├── InvestmentPanelGUI.java
                    │   ├── TooltipFactory.java
                    │   └── StyleConstants.java
                    │
                    ├── data/                                <-- Datenhandling
                    │   ├── DataManager.java
                    │   ├── JSONLoader.java
                    │   ├── SaveFileHandler.java
                    │   ├── AutoSaveTask.java
                    │   └── MigrationTool.java
                    │
                    ├── config/                              <-- Konfigurationen
                    │   ├── GameSettings.java
                    │   ├── BalanceConfig.java
                    │   ├── MarketConfig.java
                    │   └── LanguageConfig.java
                    │
                    ├── util/                                <-- Helferklassen
                    │   ├── Logger.java
                    │   ├── MathUtils.java
                    │   ├── TimeUtils.java
                    │   ├── ColorUtils.java
                    │   └── RandomUtils.java
                    │
                    └── api/                                 <-- API für Erweiterungen / Plugins
                        ├── EmpirePlugin.java
                        ├── PluginContext.java
                        ├── EmpireAPI.java
                        └── EventHooks.java
resources/
├── config/
│   ├── game_settings.json
│   ├── balance_config.json
│   └── language/
│       ├── en_us.json
│       └── de_de.json
├── data/
│   ├── resources.json
│   ├── buildings.json
│   ├── tech_tree.json
│   └── policies.json
├── gui/
│   └── themes/
│       ├── default_theme.json
│       └── retro_theme.json
└── presets/
    ├── sandbox.json
    ├── campaign_01.json
    └── scenario_capitalist_dream.json

```
