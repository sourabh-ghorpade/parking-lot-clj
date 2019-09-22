(ns amazing-parking-lot.models.parking-lot-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.models.parking-lot :refer :all]
            [amazing-parking-lot.models.car :as car]))

(deftest create-test
  (testing "creates a parking lot with the given slots"
    (let [value (create 3)]
      (is (= value {:number-of-slots 3
                    :slots           [nil nil nil]})))))

(deftest park-test
  (testing "when the parking lot is empty"
    (testing "it parks the car"
      (let [parking-lot (create 3)
            car (car/create "ABC" "white")
            result (park car parking-lot)]
        (is (= (:message result) "Parked in slot number 1"))
        (is (= (:parking-lot result) {:number-of-slots 3
                                      :slots           [car nil nil]})))))

  (testing "when the parking lot is not empty"
    (testing "and there is space available"
      (testing "it parks the car"
        (let [car_one (car/create "ABC" "white")
              car_two (car/create "PQR" "black")
              parking-lot {:number-of-slots 3
                           :slots           (vec [car_one nil nil])}
              result (park car_two parking-lot)]
          (is (= (:message result) "Parked in slot number 2"))
          (is (= (:parking-lot result) {:number-of-slots 3
                                        :slots           [car_one car_two nil]})))))
    (testing "and there is no space available"
      (testing "then it returns parking lot is full"
        (let [car_one (car/create "ABC" "white")
              car_two (car/create "PQR" "black")
              parking-lot {:number-of-slots 1
                           :slots           (vec [car_one])}
              result (park car_two parking-lot)]
          (is (= (:message result) "Parking Lot is full"))
          (is (= (:parking-lot result) {:number-of-slots 1
                                        :slots           (vec [car_one])})))))))
