(ns amazing-parking-lot.utils.utils)

(defn test-command-reader [input-commands-by-order]
  (let [times-called (atom 0)]
    (fn []
      (let [command (.get input-commands-by-order @times-called)]
        (reset! times-called (inc @times-called))
        command))))

(defn test-writer [output-capture-list-atom]
  (fn [output-message]
    (let [appended-list (conj @output-capture-list-atom output-message)]
      (reset! output-capture-list-atom appended-list))))