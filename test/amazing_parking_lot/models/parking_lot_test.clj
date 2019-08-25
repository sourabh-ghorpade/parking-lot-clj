(ns amazing-parking-lot.models.parking-lot-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.models.parking-lot :refer :all]
            [amazing-parking-lot.models.car :as car]))

(deftest create-test
  (testing "creates a parking lot with the given slots"
    (is (= (create 6) {:number-of-slots 6}))))

(deftest park-test
  (let [parking-lot {:number-of-slots 6}
        car (car/create "ABC" "white")
        result (park car parking-lot)]
    (is (and (= (:message result) "Parked in slot number 1")
             (= (:parking-lot result) {:number-of-slots 6
                                       :slots           [car]})))))
