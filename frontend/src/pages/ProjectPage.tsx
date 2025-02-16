import { AnalysisInputsTable } from "@/components/inputs/AnalysisInputsTable";
import { columns } from "@/components/inputs/Columns";
import { useAnalysisInputs } from "@/hooks/useAnalysisInput";
import { useProject } from "@/hooks/useProject";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@radix-ui/react-tabs";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Loading from "@/components/loading/Loading";
import CreateEntrypoint, {
  FileOperation,
} from "@/components/projects/CreateEntrypoint";
import Overlay from "@/components/ui/Overlay";
import CallGraphInputCreateDialog from "@/components/projects/CallGraphInputCreateDIalog";
import AnalysisInputCreateDialog from "@/components/projects/AnalysisInputCreateDialog";
import CallGraphsTab from "@/components/callgraphs/CallGraphsTab";

const ProjectPage = () => {
  const { id } = useParams();
  const [importExportDialogUp, showImportExportDialog] =
    useState<FileOperation | null>(null);
  const [activeTab, setActive] = useState<string>(
    localStorage.getItem("activeTab") || "components"
  );

  useEffect(() => {
    localStorage.setItem("activeTab", activeTab);
  }, [activeTab]);

  if (!id) return <p>Error invalid ID...</p>;

  const { data: project, isLoading: projectLoading } = useProject(id);
  const { data: inputs, isLoading: inputsLoading } = useAnalysisInputs(id);

  return (
    <div className="m-5">
      <div className="flex flex-row justify-between">
        {projectLoading ? (
          <Loading overlay={false} />
        ) : (
          <h1 className="text-2xl font-semibold">{project?.projectName}</h1>
        )}
        <CreateEntrypoint showImportExportDialog={showImportExportDialog} />
      </div>

      <Tabs
        defaultValue={activeTab}
        onValueChange={(value) => setActive(value)}
      >
        <TabsList className="flex flex-row py-2 text-center border-gray-300">
          <TabsTrigger
            value="components"
            className={
              "py-2 px-4 rounded-l-md transition-all duration-200 focus:outline-none" +
              (activeTab === "components" ? " bg-gray-300" : "")
            }
          >
            Components
          </TabsTrigger>
          <TabsTrigger
            value="callgraphs"
            className={
              "py-2 px-4 rounded-r-md transition-all duration-200 focus:outline-none" +
              (activeTab === "callgraphs" ? " bg-gray-300" : "")
            }
          >
            Call Graphs
          </TabsTrigger>
        </TabsList>

        <TabsContent value="components">
          {inputsLoading ? (
            <Loading overlay={false} />
          ) : (
            <AnalysisInputsTable columns={columns} data={inputs!} />
          )}
        </TabsContent>

        <TabsContent value="callgraphs">
          <CallGraphsTab projectId={id} />
        </TabsContent>
      </Tabs>
      {importExportDialogUp != null && (
        <Overlay width="w-1/2" closeFunc={() => showImportExportDialog(null)}>
          {importExportDialogUp == FileOperation.IMPORT &&
            activeTab === "callgraphs" && (
              <CallGraphInputCreateDialog
                projectId={id}
                closeDialog={() => showImportExportDialog(null)}
              />
            )}
          {importExportDialogUp == FileOperation.IMPORT &&
            activeTab === "components" && (
              <AnalysisInputCreateDialog
                projectId={id}
                closeDialog={() => showImportExportDialog(null)}
              />
            )}
        </Overlay>
      )}
    </div>
  );
};

export default ProjectPage;
