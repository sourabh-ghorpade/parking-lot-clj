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
                                      :slots           [car nil nil]}))
        (is (= (:action result) {:name        :park-car
                                 :slot-number 1
                                 :car         car}))
        (is (= (:response-code result) (status-codes :state-changed))))))

  (testing "when the parking lot is not empty"
    (testing "and there is space available"
      (testing "it parks the car and returns a state changed event"
        (let [car_one (car/create "ABC" "white")
              car_two (car/create "PQR" "black")
              parking-lot {:number-of-slots 3
                           :slots           (vec [car_one nil nil])}
              result (park car_two parking-lot)]
          (is (= (:message result) "Parked in slot number 2"))
          (is (= (:parking-lot result) {:number-of-slots 3
                                        :slots           [car_one car_two nil]}))
          (is (= (:action result) {:name        :park-car
                                   :slot-number 2
                                   :car         car_two}))
          (is (= (:response-code result) (status-codes :state-changed))))))

    (testing "and there is no space available"
      (testing "then it returns parking lot is full"
        (let [car_one (car/create "ABC" "white")
              car_two (car/create "PQR" "black")
              parking-lot {:number-of-slots 1
                           :slots           (vec [car_one])}
              result (park car_two parking-lot)]
          (is (= (:message result) "Parking Lot is full"))
          (is (= (:parking-lot result) {:number-of-slots 1
                                        :slots           (vec [car_one])}))
          (is (= (status-codes :parking-lot-full) (:response-code result)))
          (is (= (:action result) {:name :no-operation})))))))

(deftest leave-test
  (testing "when the slot number is valid"
    (testing "when there is a car against the given slot number"
      (testing "it un-parks the car with a message and frees the slot"
        (let [car_one (car/create "ABC" "white")
              parking-lot {:number-of-slots 1
                           :slots           [car_one]}
              leave-result (leave "1" parking-lot)]
          (is (= (:message leave-result) "Un-Parked Car ABC at slot 1"))
          (is (= (:parking-lot leave-result) {:number-of-slots 1
                                              :slots           [nil]})))))
    (testing "when there is no car against the given slot number"
      (testing "it returns error"
        (let [parking-lot {:number-of-slots 1
                           :slots           [nil]}
              leave-result (leave "1" parking-lot)]
          (is (= (:message leave-result) "No car parked at the given slot"))
          (is (= (:parking-lot leave-result) {:number-of-slots 1
                                              :slots           [nil]})))))))
