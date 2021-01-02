(ns amazing-parking-lot.input.runner-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.input.runner :refer [run]]
            [amazing-parking-lot.utils.utils :as util]))

(deftest run-test
  (testing "it processes and outputs all inputs till input is not the exit command"
    (let [output-capture-list (atom (vec []))
          input-commands ["create_parking_lot 2"
                          "exit"]
          expected-output ["Created parking lot with 2 slots"]]
      (run (util/test-command-reader input-commands) (util/test-writer output-capture-list))
      (is (= expected-output @output-capture-list)))))
