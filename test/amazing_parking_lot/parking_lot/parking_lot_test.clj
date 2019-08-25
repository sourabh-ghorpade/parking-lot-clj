(ns amazing-parking-lot.parking-lot.parking-lot-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.parking-lot.parking-lot :refer :all]))

(deftest create-test
  (testing "creates a parking lot with the given slots"
    (is (= (create 6) {:number-of-slots 6}))))

(deftest park-test
  (let [parking-lot {:number-of-slots 6}
        licence-number "ABC"
        color "white"
        result (park licence-number color parking-lot)]
    (is (and (= (:message result) "Parked in slot number 1")
             (= (:parking-lot result) {:number-of-slots 6
                                       :slots [{:licence-number licence-number
                                                :color color}]})))))
