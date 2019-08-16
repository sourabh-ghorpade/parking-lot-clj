(ns parking-lot.command.executor-test
  (:require [clojure.test :refer :all]
            [parking-lot.command.executor :refer :all]
            [parking-lot.input.command-argument-option :refer :all]))

(deftest execute-test
  (testing "it executes the command and sets the result"
    (let [create-command (create-valid-option "create_parking_lot" [6] "")
          resulting-option (execute create-command)]
      (is (= (result resulting-option) "Created parking lot with 6 slots")))))
