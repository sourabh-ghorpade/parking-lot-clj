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
        (let [car-one (car/create "ABC" "white")
              car-two (car/create "PQR" "black")
              parking-lot {:number-of-slots 3
                           :slots           (vec [car-one nil nil])}
              result (park car-two parking-lot)]
          (is (= (:parking-lot result) {:number-of-slots 3
                                        :slots           [car-one car-two nil]}))
          (is (= (:action result) {:name        :park-car
                                   :slot-number 2
                                   :car         car-two}))
          (is (= (:response-code result) (event/status-codes :car-parked))))))

    (testing "and there is no space available"
      (testing "then it returns parking lot is full"
        (let [car-one (car/create "ABC" "white")
              car-two (car/create "PQR" "black")
              parking-lot {:number-of-slots 1
                           :slots           (vec [car-one])}
              result (park car-two parking-lot)]
          (is (= (:parking-lot result) {:number-of-slots 1
                                        :slots           (vec [car-one])}))
          (is (= (event/status-codes :parking-lot-full) (:response-code result)))
          (is (= (:action result) {:name :no-operation})))))))

(deftest leave-test
  (testing "when the slot number is valid"
    (testing "and there is a car against the given slot number"
      (testing "it un-parks the car and returns a car un-parked event"
        (let [car-one (car/create "ABC" "white")
              parking-lot {:number-of-slots 1
                           :slots           [car-one]}
              leave-result (leave "1" parking-lot)]
          (is (= (:parking-lot leave-result) {:number-of-slots 1
                                              :slots           [nil]}))
          (is (= (event/status-codes :car-un-parked) (:response-code leave-result)))
          (is (= (:action leave-result) {:name        :car-un-parked
                                         :slot-number "1"
                                         :car         car-one}))
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
          (is (= :no-operation (get-in leave-result [:action :name]))))))))

(deftest registration-numbers-for-cars-with-colour-test
  (testing "it returns a list of registration numbers for cars of given color"
    (let [car-one (car/create "ABC" "white")
          car-two (car/create "PQR" "red")
          car-three (car/create "XYZ" "white")
          parking-lot {:number-of-slots 3
                       :slots           [car-one car-two car-three]}
          result (registration-numbers-for-cars-with-colour "white" parking-lot)
          expected-action {:name         :query
                           :query-result ["ABC" "XYZ"]}]
      (is (= (event/status-codes :query) (:response-code result)))
      (is (= expected-action (:action result))))))

(deftest slot-numbers-for-cars-with-colour-test
  (testing "it returns a list of registration numbers for cars of given color"
    (let [car-one (car/create "ABC" "white")
          car-two (car/create "PQR" "red")
          car-three (car/create "XYZ" "white")
          parking-lot {:number-of-slots 3
                       :slots           [car-one car-two car-three]}
          result (slot-numbers-for-cars-with-colour "white" parking-lot)
          expected-action {:name         :query
                           :query-result [1 3]}]
      (is (= (event/status-codes :query) (:response-code result)))
      (is (= expected-action (:action result))))))

(deftest slot-number-for-registration-number-test
  (testing "it returns a slot number of the car of given registration number"
    (let [car-one (car/create "ABC" "white")
          car-two (car/create "PQR" "red")
          car-three (car/create "XYZ" "white")
          parking-lot {:number-of-slots 3
                       :slots           [car-one car-two car-three]}
          result (slot-number-for-registration-number "PQR" parking-lot)
          expected-action {:name         :query
                           :query-result [2]}]
      (is (= (event/status-codes :query) (:response-code result)))
      (is (= expected-action (:action result))))))
