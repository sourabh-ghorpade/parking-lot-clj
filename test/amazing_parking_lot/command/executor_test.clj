(ns amazing-parking-lot.command.executor-test
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.command.executor :refer :all]
            [amazing-parking-lot.models.parking-lot :as parking-lot]
            [amazing-parking-lot.input.command-argument-option :refer :all]))

(deftest execute-test
  (testing "when the command is create parking lot"
    (testing "it executes the command and sets the message and parking lot"
      (let [create-command (create-valid-option "create_parking_lot" ["6"])
            resulting-option (execute create-command)
            expected-parking-lot {:number-of-slots 6, :slots [nil nil nil nil nil nil]}]
        (is (= (message resulting-option) "Created parking lot with 6 slots"))
        (is (= (parking-lot resulting-option) expected-parking-lot)))))

  (testing "when the command is park a car"
    (testing "it executes the command and sets the message and updated parking lot"
      (with-redefs [parking-lot/park (fn [_ _] {:message     "Parked in slot number 1"
                                                :parking-lot :expected-parking-lot})]
        (let [park-command (create-valid-option "park" ["ABC" "White"])
              resulting-option (execute park-command)]
          (is (= (message resulting-option) "Parked in slot number 1"))
          (is (= (parking-lot resulting-option) :expected-parking-lot))))))
  (testing "when the command is leave a car"
    (testing "it executes the command, sets the message and returns the result"
      (let [actual-slot-number (atom nil)
            actual-parking-lot (atom nil)]
        (with-redefs [parking-lot/leave (fn [received-slot-number received-parking-lot]
                                          (reset! actual-slot-number received-slot-number)
                                          (reset! actual-parking-lot received-parking-lot)
                                          {:message     "Un-parked car 1"
                                           :parking-lot :expected-parking-lot})]
          (let [leave-command (assoc (create-valid-option "leave" ["1"]) :parking-lot :expected-parking-lot)
                resulting-option (execute leave-command)]
            (is (= "Un-parked car 1" (message resulting-option)))
            (is (= (parking-lot resulting-option) :expected-parking-lot))
            (is (= "1" @actual-slot-number))
            (is (= :expected-parking-lot @actual-parking-lot))))))))
