# architecture-android

Use Dagger to support DI, and use databinding to support MVVM.

##MVP

###model 
It supports two different data sources as http and sqlite, use retrofit and SqlBrite to support rxJava, and use the same interface as dataprovider.

###presenter
It use dataprovider to get datas, process business logic, and fill the view.

###view
It support user interaction.

##Dagger2

###module
Use provider java annotation to provider dependency interface, function name can anyone.

###component
The bridge between module and inject, and can dependent other component.

###inject
Use inject java annotation to support DI, and can use constructor to inject module provider interface. 

