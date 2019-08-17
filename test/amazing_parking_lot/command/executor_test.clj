(ns amazing-parking-lot.command.executor-test
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.command.executor :refer :all]
            [amazing-parking-lot.parking-lot.parking-lot :as parking-lot]
            [amazing-parking-lot.input.command-argument-option :refer :all]))

(deftest execute-test
  (testing "it executes the command and sets the message and parking lot"
    (with-redefs [parking-lot/create (fn [_] :expected-parking-lot)]
      (let [create-command (create-valid-option "create_parking_lot" [6])
            resulting-option (execute create-command)]
        (is (= (message resulting-option) "Created parking lot with 6 slots"))
        (is (= (parking-lot resulting-option) :expected-parking-lot))))))
