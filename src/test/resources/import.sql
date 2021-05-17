INSERT INTO skill_hours (id, available, earned, used) VALUES (2, 0, 0, 0);
INSERT INTO skill_hours (id, available, earned, used) VALUES (6, 0, 0, 0);
INSERT INTO skill_hours (id, available, earned, used) VALUES (3, 0, 0, 0);
INSERT INTO skill_hours (id, available, earned, used) VALUES (4, 0, 0, 0);


INSERT INTO users (id, username, password, firstname, lastname, email, enabled, rating, skill_hours_id) VALUES (7, 'Tester', '$2a$10$L/oAPj/4SwVMvvZlRTFjIukUapumeb/4CtSj5zzTDO6ziNR1dWfFO', 'Tester', 'Tester', 'st55409@upce.cz', true, 0, 3);
INSERT INTO users (id, username, password, firstname, lastname, email, enabled, rating, skill_hours_id) VALUES (8, 'Test', '$2a$10$eDo/lvFNKwmI6zKnF.HP3ODflAPMfUWHNJQc9DUt8iN4FXIfBzQi2', 'Test', 'Test', 'tester@email.cz', false, 0, 4);
INSERT INTO users (id, username, password, firstname, lastname, email, enabled, rating, skill_hours_id) VALUES (1, 'user1', '$2a$10$TaXtJromZy7xjeCc8YX.8urBrDfTnHDon.REzv8azEbKCShlnxKZ2', 'Adam', 'Novak', 'matyas.leisky@addai.cz', true, 4, 6);
--INSERT INTO users (id, username, password, firstname, lastname, email, enabled, rating, skill_hours_id) VALUES (6, 'jovkhar.issayev@addai.cz', '$2a$10$TaXtJromZy7xjeCc8YX.8urBrDfTnHDon.REzv8azEbKCShlnxKZ2', 'Dzhochar', 'Isaev', 'jovkhar.issayev@addai.cz', true, 0, 6);
--
INSERT INTO categories (id, name, image) VALUES (1, 'Cooking', '../assets/images/cooking.png');
INSERT INTO categories (id, name, image) VALUES (6, 'IT', '../assets/images/it.png');
INSERT INTO categories (id, name, image) VALUES (5, 'Sport', '../assets/images/judo.jpg');
INSERT INTO categories (id, name, image) VALUES (4, 'Education', '../assets/images/education.png');
INSERT INTO categories (id, name, image) VALUES (8, 'Music', '../assets/images/default.jpg');
INSERT INTO categories (id, name, image) VALUES (3, 'Art', '../assets/images/default.jpg');
INSERT INTO categories (id, name, image) VALUES (2, 'Consulting', '../assets/images/default.jpg');
INSERT INTO categories (id, name, image) VALUES (7, 'Car', '../assets/images/default.jpg');

--
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (1, 'English', 'NEEDS', 4, 'Looking for somebody, who can help me to improve my English skills', null, 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (4, 'Logo Consultation', 'SKILLS', 3, 'I am a designer with five years of professional experience. I would love to be able to give you some feedback on your logo design', 'I will spend 30 minutes critiquing your design and give you some advice on how to move forward.', 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (5, ' Schach', 'SKILLS', 4, 'I can teach you how to play chess and basic principles and strategies', null, 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (2, 'Deutsch', 'SKILLS', 4, 'Can help with Deutsch', null, 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (3, 'Karate Training', 'SKILLS', 5, 'Betreibe seit 20 Jahren traditionelles Shotokan Karate (3.Dan) und habe auch in den vergangenen Jahren einige Titel geholt (EM Gold, WM Bronze, etc.) Ich freu mich mein Wissen weiterzugeben!', null, 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (8, ' Looking to learn web development ', 'NEEDS', 6, 'I am interested in learning programming :) Please contact me at octodsad32e3@gmail.com I speak fluent English, Russian. Basic German', null, 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (9, 'Vegetarische Küche', 'SKILLS', 1, 'Ich bin gelernte Köchin, habe aber nach meiner Ausbildung vorwiegend im Service und nicht in der Küche gearbeitet. Trotzdem koche und backe ich gerne leidenschaftlich vegetarisch, vor allem auch österreichische Küche, und zaubere gern Gerichte aus dem was der Kühlschrank hergibt.', null, 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (6, 'Java programming', 'NEEDS', 6, 'Looking for somebody, who can help me to improve my English skills', null, 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (7, ' Android / Google Solutions ', 'NEEDS', 6, 'I want to learn Android development', null, 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (10, 'Testing', 'NEEDS', 1, 'TestingTesting', 'Testing', 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (11, 'Testing', 'SKILLS', 6, 'TestingTesting', 'Testing', 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (12, 'NNPIA_test', 'NEEDS', 6, 'NNPIA_test', 'NNPIA_test', 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (13, 'NNPIA_TEST', 'NEEDS', 6, 'NNPIA_TEST', 'NNPIA_TEST', 1);
INSERT INTO posts (id, title, type, category_id, description, details, user_id) VALUES (14, 'Spring Framework', 'SKILLS', 6, 'Can help with Spring framework', 'Imporve your porgramming skills with me using Spring Framework', 1);