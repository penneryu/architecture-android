# architecture-android

##MVP

###model 
It supports two different data sources as http and sqlite, use retrofit and SqlBrite to support rxJava, and use the same interface as dataprovider.

###presenter
It use dataprovider to get datas, process business logic, and fill the view.

###view
It support user interaction.
