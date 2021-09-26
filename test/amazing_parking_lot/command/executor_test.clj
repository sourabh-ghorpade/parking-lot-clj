(ns amazing-parking-lot.command.executor-test
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.command.executor :refer :all]
            [amazing-parking-lot.models.parking-lot :as parking-lot]
            [amazing-parking-lot.input.command-argument-option :refer :all]
            [amazing-parking-lot.models.event :as event]
            [amazing-parking-lot.models.car :as car]))

(deftest execute-test
  (testing "when the command is create parking lot"
    (testing "it executes the command and sets the message and parking lot"
      (let [create-command (create-valid-option "create_parking_lot" ["6"])
            resulting-option (execute create-command)
            expected-parking-lot {:number-of-slots 6, :slots [nil nil nil nil nil nil]}]
        (is (= (message resulting-option) "Created parking lot with 6 slots"))
        (is (= (parking-lot resulting-option) expected-parking-lot)))))
  (let [car-one (car/create "ABC" "white")
        car-two (car/create "PQR" "red")
        car-three (car/create "XYZ" "white")
        parking-lot {:number-of-slots 4
                     :slots           [car-one car-two car-three nil]}]
    (testing "when the command is park a car"
      (testing "it creates a parked message and sets the updated parking lot"
        (let [park-command (add-parking-lot (create-valid-option "park" ["FOP" "White"]) parking-lot)
              resulting-option (execute park-command)
              expected-parking-lot {:number-of-slots 4,
                                    :slots           [{:color "white", :license-number "ABC"}
                                                      {:color "red", :license-number "PQR"}
                                                      {:color "white", :license-number "XYZ"}
                                                      {:color "White", :license-number "FOP"}]}]
          (is (= "Parked in slot number 4" (message resulting-option)))
          (is (= expected-parking-lot (:parking-lot resulting-option))))))
    (testing "when the command is leave a car"
      (testing "it executes the command, sets the message and returns the result"
        (let [leave-command (add-parking-lot (create-valid-option "leave" ["1"]) parking-lot)
              resulting-option (execute leave-command)
              expected-parking-lot {:number-of-slots 4, :slots [nil
                                                                {:color "red", :license-number "PQR"}
                                                                {:color "white", :license-number "XYZ"}
                                                                nil]}]
          (is (= "Un-parked car ABC at slot 1" (message resulting-option)))
          (is (= expected-parking-lot (:parking-lot resulting-option))))))
    (testing "when the command is registration_numbers_for_cars_with_colour"
      (testing "it returns the list of registration numbers of cars with specified color"
        (let [registration-numbers-for-cars-with-colour (assoc (create-valid-option "registration_numbers_for_cars_with_colour" ["white"])
                                                          :parking-lot parking-lot)
              resulting-option (execute registration-numbers-for-cars-with-colour)]
          (is (= "ABC, XYZ" (message resulting-option)))
          (is (= parking-lot (:parking-lot resulting-option))))))
    (testing "when the command is slot_numbers_for_cars_with_colour"
      (testing "it returns the list of slot numbers of cars with specified color"
        (let [slot-numbers-for-cars-with-colour (assoc (create-valid-option "slot_numbers_for_cars_with_colour" ["white"])
                                                  :parking-lot parking-lot)
              resulting-option (execute slot-numbers-for-cars-with-colour)]
          (is (= "1, 3" (message resulting-option)))
          (is (= parking-lot (:parking-lot resulting-option))))))
    (testing "when the command is slot_number_for_registration_number"
      (testing "it returns the list of slot number of the car with specified registration number"
        (let [slot-numbers-for-cars-with-colour (assoc (create-valid-option "slot_number_for_registration_number" ["ABC"])
                                                  :parking-lot parking-lot)
              resulting-option (execute slot-numbers-for-cars-with-colour)]
          (is (= "1" (message resulting-option)))
          (is (= parking-lot (:parking-lot resulting-option))))))))
