INSERT INTO cfig_config_entry (CODE, NAME, SCOPE, DESCRIPTION, ITEM_STRUCTURE_TYPE, CREATED_TIME, UPDATED_TIME) VALUES ('MAP_1', 'mapDemo', 'app,web,service', 'map demo', '2', '2019-08-03 21:01:27', '2019-08-03 21:01:27');
INSERT INTO cfig_config_entry (CODE, NAME, SCOPE, DESCRIPTION, ITEM_STRUCTURE_TYPE, CREATED_TIME, UPDATED_TIME) VALUES ('LIST_1', 'listDemo', 'app,web', 'list demo', '1', '2019-08-03 23:25:03', '2019-08-03 23:25:03');
INSERT INTO cfig_config_entry (CODE, NAME, SCOPE, DESCRIPTION, ITEM_STRUCTURE_TYPE, CREATED_TIME, UPDATED_TIME) VALUES ('TREE_1', 'treeDemo', 'app,web', 'tree demo', '3', '2019-08-03 23:27:27', '2019-08-03 23:27:27');


INSERT INTO cfig_config_item (ID, ENTRY_CODE, NAME, VALUE_TYPE, VALUE, ORDER, KEY, TREE_LEVEL, PARENT_ID, CREATED_TIME, UPDATED_TIME) VALUES ('1', 'MAP_1', 'item1', '2', 'v1', '1', 'k1', NULL, NULL, '2019-08-03 21:02:35', '2019-08-03 21:02:35');
INSERT INTO cfig_config_item (ID, ENTRY_CODE, NAME, VALUE_TYPE, VALUE, ORDER, KEY, TREE_LEVEL, PARENT_ID, CREATED_TIME, UPDATED_TIME) VALUES ('2', 'MAP_1', 'item2', '2', 'v2', '2', 'k2', NULL, NULL, '2019-08-03 21:02:57', '2019-08-03 22:08:15');
INSERT INTO cfig_config_item (ID, ENTRY_CODE, NAME, VALUE_TYPE, VALUE, ORDER, KEY, TREE_LEVEL, PARENT_ID, CREATED_TIME, UPDATED_TIME) VALUES ('3', 'LIST_1', '状态1', '2', '1', '1', NULL, NULL, NULL, '2019-08-03 23:26:06', '2019-08-03 23:26:06');
INSERT INTO cfig_config_item (ID, ENTRY_CODE, NAME, VALUE_TYPE, VALUE, ORDER, KEY, TREE_LEVEL, PARENT_ID, CREATED_TIME, UPDATED_TIME) VALUES ('4', 'LIST_1', '状态2', '2', '2', '2', NULL, NULL, NULL, '2019-08-03 23:26:39', '2019-08-03 23:26:47');
INSERT INTO cfig_config_item (ID, ENTRY_CODE, NAME, VALUE_TYPE, VALUE, ORDER, KEY, TREE_LEVEL, PARENT_ID, CREATED_TIME, UPDATED_TIME) VALUES ('5', 'TREE_1', '山西', '2', 'shangxi', '1', NULL, '1', NULL, '2019-08-03 23:28:11', '2019-08-03 23:28:11');
INSERT INTO cfig_config_item (ID, ENTRY_CODE, NAME, VALUE_TYPE, VALUE, ORDER, KEY, TREE_LEVEL, PARENT_ID, CREATED_TIME, UPDATED_TIME) VALUES ('6', 'TREE_1', '长治', '2', 'changzhi', '2', NULL, '2', '5', '2019-08-03 23:28:35', '2019-08-03 23:28:35');