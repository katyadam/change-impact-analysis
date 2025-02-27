import { FC } from "react";
import { Button } from "../ui/button";
import { useCommGraphDiffs } from "@/hooks/useCommGraph";
import dayjs from "dayjs";
import { Separator } from "../ui/separator";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../ui/select";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import { CircleHelp } from "lucide-react";

type NavbarType = {
  analysisInputId: string;
  showComparisons: boolean;
  compareBtnClick: (val: boolean) => void;
  setShowComparisons: (val: boolean) => void;
  setSelectedCommGraphDiff: (selectedCommGraphDiff: string) => void;
  hintComponent?: React.ReactNode;
};

const Navbar: FC<NavbarType> = ({
  analysisInputId,
  showComparisons,
  compareBtnClick,
  setSelectedCommGraphDiff,
  setShowComparisons,
  hintComponent,
}) => {
  const { data: commGraphDiffs, isLoading } =
    useCommGraphDiffs(analysisInputId);
  if (isLoading) return <p>Loading..</p>;

  const formatDate = (sample: string) => {
    return dayjs(sample).format("MMMM DD, YYYY, HH:mm:ss");
  };

  return (
    <div className="flex flex-row ml-5 gap-4 justify-start items-center w-full h-12">
      <Button
        onClick={() => {
          setShowComparisons(!showComparisons);
          setSelectedCommGraphDiff("None");
        }}
        variant="outline"
      >
        {showComparisons ? "Show graph" : "Show differences"}
      </Button>
      <Separator orientation="vertical" color="black" />
      {showComparisons ? (
        <>
          {commGraphDiffs && (
            <Select
              defaultValue="None"
              onValueChange={(value) => setSelectedCommGraphDiff(value)}
            >
              <SelectTrigger className="w-[280px]">
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="None">None</SelectItem>
                {commGraphDiffs.map((diff) => (
                  <SelectItem key={diff.id} value={diff.id.toString()}>
                    {formatDate(diff.createdAt)}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          )}
          <Popover modal={false}>
            <PopoverTrigger className="relative flex flex-col items-center mx-5 cursor-pointer">
              <label className="text-gray-500 mb-1">Hint</label>
              <div className="relative">
                <CircleHelp />
              </div>
            </PopoverTrigger>

            <PopoverContent className="w-full">{hintComponent}</PopoverContent>
          </Popover>
        </>
      ) : (
        <>
          <Button
            onClick={() => compareBtnClick(true)}
            className=""
            variant="outline"
          >
            Compare
          </Button>
        </>
      )}
    </div>
  );
};

export default Navbar;
