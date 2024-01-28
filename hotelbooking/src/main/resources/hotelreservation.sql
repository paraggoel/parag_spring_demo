CREATE SCHEMA `hotelreservation` ;


INSERT INTO `hotelreservation`.`hotel` (`id`, `address`, `name`) VALUES ('1', 'Berlin', 'IHG');
INSERT INTO `hotelreservation`.`hotel` (`id`, `address`, `name`) VALUES ('2', 'Mumbai', 'Taj');


INSERT INTO `hotelreservation`.`room` (`id`, `price`, `hotel_id`, `room_count`, `room_type`) VALUES ('1', '100', '1', '5', 'Standard');
INSERT INTO `hotelreservation`.`room` (`id`, `price`, `hotel_id`, `room_count`, `room_type`) VALUES ('2', '150', '1', '7', 'Luxury');
INSERT INTO `hotelreservation`.`room` (`id`, `price`, `hotel_id`, `room_count`, `room_type`) VALUES ('3', '100', '2', '10', 'Standard');
INSERT INTO `hotelreservation`.`room` (`id`, `price`, `hotel_id`, `room_count`, `room_type`) VALUES ('4', '170', '2', '15', 'Luxury');

