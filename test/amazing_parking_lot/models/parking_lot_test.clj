(ns amazing-parking-lot.models.parking-lot-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.models.parking-lot :refer :all]
            [amazing-parking-lot.models.car :as car]))

(deftest create-test
  (testing "creates a parking lot with the given slots"
    (let [value (create 3)]
      (is (= value {:number-of-slots 3
                    :slots           [{} {} {}]})))))

(deftest park-test
  (let [parking-lot (create 3)
        car (car/create "ABC" "white")
        result (park car parking-lot)]
    (is (= (:message result) "Parked in slot number 1"))
    (is (= (:parking-lot result) {:number-of-slots 3
                                  :slots           [car]}))))
