(ns amazing-parking-lot.models.parking-lot-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.models.parking-lot :refer :all]
            [amazing-parking-lot.models.car :as car]
            [amazing-parking-lot.models.event :as event])
  (:import (clojure.lang PersistentVector)))

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
        (is (= (:parking-lot result) {:number-of-slots 3
                                      :slots           [car nil nil]}))
        (is (= (:action result) {:name        :park-car
                                 :slot-number 1
                                 :car         car}))
        (is (= (:response-code result) (event/status-codes :car-parked))))))

  (testing "when the parking lot is not empty"
    (testing "and there is space available"
      (testing "it parks the car and returns a car parked event"
        (let [car_one (car/create "ABC" "white")
              car_two (car/create "PQR" "black")
              parking-lot {:number-of-slots 3
                           :slots           (vec [car_one nil nil])}
              result (park car_two parking-lot)]
          (is (= (:parking-lot result) {:number-of-slots 3
                                        :slots           [car_one car_two nil]}))
          (is (= (:action result) {:name        :park-car
                                   :slot-number 2
                                   :car         car_two}))
          (is (= (:response-code result) (event/status-codes :car-parked))))))

    (testing "and there is no space available"
      (testing "then it returns parking lot is full"
        (let [car_one (car/create "ABC" "white")
              car_two (car/create "PQR" "black")
              parking-lot {:number-of-slots 1
                           :slots           (vec [car_one])}
              result (park car_two parking-lot)]
          (is (= (:parking-lot result) {:number-of-slots 1
                                        :slots           (vec [car_one])}))
          (is (= (event/status-codes :parking-lot-full) (:response-code result)))
          (is (= (:action result) {:name :no-operation})))))))

(deftest leave-test
  (testing "when the slot number is valid"
    (testing "and there is a car against the given slot number"
      (testing "it un-parks the car and returns a car un-parked event"
        (let [car_one (car/create "ABC" "white")
              parking-lot {:number-of-slots 1
                           :slots           [car_one]}
              leave-result (leave "1" parking-lot)]
          (is (= (:parking-lot leave-result) {:number-of-slots 1
                                              :slots           [nil]}))
          (is (= (event/status-codes :car-un-parked) (:response-code leave-result)))
          (is (= (:action leave-result) {:name        :car-un-parked
                                         :slot-number "1"
                                         :car         car_one}))
          ; The following check ensures that the slots are a vector
          ; That will enable the assoc for further parks.
          (is (= PersistentVector (type (get-in leave-result [:parking-lot :slots])))))))
    (testing "when there is no car against the given slot number"
      (testing "it returns a no operation event with the response code as car not found"
        (let [parking-lot {:number-of-slots 1
                           :slots           [nil]}
              leave-result (leave "1" parking-lot)]
          (is (= (:parking-lot leave-result) {:number-of-slots 1
                                              :slots           [nil]}))
          (is (= (event/status-codes :car-not-found) (:response-code leave-result)))
          (is (= (event/status-codes :car-not-found) (:response-code leave-result)))
          (is (= :no-operation (get-in leave-result [:action :name]))))))))
